package com.forkprint.app.places

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.forkprint.app.data.local.dao.CachedPlaceDao
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.cos

@Singleton
class GooglePlacesProvider @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cachedPlaceDao: CachedPlaceDao,
) : PlacesProvider {
    private val apiKey: String by lazy { readApiKey() }

    override suspend fun searchRestaurants(query: String): List<PlaceCandidate> {
        val trimmed = query.trim()
        if (trimmed.isBlank()) return emptyList()
        val cached = cachedPlaceDao.search(trimmed).map { it.toPlaceCandidate() }
        if (apiKey.isBlank()) return cached
        return runCatching {
            val body = JSONObject()
                .put("textQuery", trimmed)
                .put("includedType", "restaurant")
                .put("pageSize", 10)
            requestPlaces("https://places.googleapis.com/v1/places:searchText", body)
        }.onSuccess { cache(it) }
            .getOrElse { error ->
                Log.w(TAG, "Text search failed; using cached places", error)
                cached
            }
    }

    override suspend fun nearbyRestaurants(latitude: Double, longitude: Double): List<PlaceCandidate> {
        val cached = cachedPlaceDao.nearbyBounds(latitude, longitude).map { it.toPlaceCandidate() }
        if (apiKey.isBlank()) return cached
        return runCatching {
            val includedTypes = JSONArray(listOf("restaurant", "cafe", "bakery", "bar", "meal_takeaway", "meal_delivery"))
            val circle = JSONObject()
                .put("center", JSONObject().put("latitude", latitude).put("longitude", longitude))
                .put("radius", NEARBY_RADIUS_METERS)
            val body = JSONObject()
                .put("includedTypes", includedTypes)
                .put("maxResultCount", 10)
                .put("rankPreference", "DISTANCE")
                .put("locationRestriction", JSONObject().put("circle", circle))
            requestPlaces("https://places.googleapis.com/v1/places:searchNearby", body)
        }.onSuccess { cache(it) }
            .getOrElse { error ->
                Log.w(TAG, "Nearby search failed; using cached places", error)
                cached
            }
    }

    override suspend fun placeDetails(placeId: String): PlaceCandidate? {
        val cached = cachedPlaceDao.findByPlaceId(placeId)?.toPlaceCandidate()
        if (apiKey.isBlank()) return cached
        return runCatching {
            requestPlace("https://places.googleapis.com/v1/places/$placeId")
        }.onSuccess { candidate -> candidate?.toCachedPlaceEntity()?.let { cachedPlaceDao.upsert(it) } }
            .getOrElse { error ->
                Log.w(TAG, "Place details failed; using cached place", error)
                cached
            }
    }

    private suspend fun requestPlaces(endpoint: String, body: JSONObject): List<PlaceCandidate> = withContext(Dispatchers.IO) {
        val response = postJson(endpoint, body)
        val places = JSONObject(response).optJSONArray("places") ?: JSONArray()
        (0 until places.length()).mapNotNull { index -> parsePlace(places.optJSONObject(index)) }
    }

    private suspend fun requestPlace(endpoint: String): PlaceCandidate? = withContext(Dispatchers.IO) {
        val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            connectTimeout = TIMEOUT_MS
            readTimeout = TIMEOUT_MS
            setRequestProperty("X-Goog-Api-Key", apiKey)
            setRequestProperty("X-Goog-FieldMask", DETAIL_FIELD_MASK)
        }
        try {
            val code = connection.responseCode
            val stream = if (code in 200..299) connection.inputStream else connection.errorStream
            val text = stream.bufferedReader().use { it.readText() }
            if (code !in 200..299) error("Places HTTP $code: $text")
            parsePlace(JSONObject(text))
        } finally {
            connection.disconnect()
        }
    }

    private fun postJson(endpoint: String, body: JSONObject): String {
        val bytes = body.toString().toByteArray(Charsets.UTF_8)
        val connection = (URL(endpoint).openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = TIMEOUT_MS
            readTimeout = TIMEOUT_MS
            doOutput = true
            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("X-Goog-Api-Key", apiKey)
            setRequestProperty("X-Goog-FieldMask", SEARCH_FIELD_MASK)
        }
        try {
            connection.outputStream.use { it.write(bytes) }
            val code = connection.responseCode
            val stream = if (code in 200..299) connection.inputStream else connection.errorStream
            val text = stream.bufferedReader().use { it.readText() }
            if (code !in 200..299) error("Places HTTP $code: $text")
            return text
        } finally {
            connection.disconnect()
        }
    }

    private fun parsePlace(json: JSONObject?): PlaceCandidate? {
        if (json == null) return null
        val id = json.optString("id").takeIf { it.isNotBlank() } ?: return null
        val location = json.optJSONObject("location")
        val types = json.optJSONArray("types") ?: JSONArray()
        return PlaceCandidate(
            name = json.optJSONObject("displayName")?.optString("text")?.takeIf { it.isNotBlank() } ?: json.optString("name", "Restaurant"),
            address = json.optString("formattedAddress").takeIf { it.isNotBlank() },
            latitude = location?.optDouble("latitude"),
            longitude = location?.optDouble("longitude"),
            googlePlaceId = id,
            categories = (0 until types.length()).mapNotNull { types.optString(it).takeIf(String::isNotBlank) },
            fetchedAt = Instant.now(),
        )
    }

    private suspend fun cache(places: List<PlaceCandidate>) {
        val entities = places.mapNotNull { it.toCachedPlaceEntity() }
        if (entities.isNotEmpty()) cachedPlaceDao.upsertAll(entities)
    }

    private suspend fun CachedPlaceDao.nearbyBounds(latitude: Double, longitude: Double) = nearby(
        minLatitude = latitude - BOUNDING_BOX_DEGREES,
        maxLatitude = latitude + BOUNDING_BOX_DEGREES,
        minLongitude = longitude - (BOUNDING_BOX_DEGREES / cos(Math.toRadians(latitude)).coerceAtLeast(0.25)),
        maxLongitude = longitude + (BOUNDING_BOX_DEGREES / cos(Math.toRadians(latitude)).coerceAtLeast(0.25)),
    )

    private fun readApiKey(): String {
        return runCatching {
            val appInfo = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            appInfo.metaData?.getString("com.google.android.geo.API_KEY").orEmpty()
        }.getOrDefault("")
    }

    private companion object {
        const val TAG = "GooglePlacesProvider"
        const val TIMEOUT_MS = 8_000
        const val NEARBY_RADIUS_METERS = 120.0
        const val BOUNDING_BOX_DEGREES = 0.01
        const val SEARCH_FIELD_MASK = "places.id,places.displayName,places.formattedAddress,places.location,places.types"
        const val DETAIL_FIELD_MASK = "id,displayName,formattedAddress,location,types"
    }
}
