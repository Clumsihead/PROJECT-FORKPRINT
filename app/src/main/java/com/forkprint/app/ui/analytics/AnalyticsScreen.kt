package com.forkprint.app.ui.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.forkprint.app.ui.ForkprintViewModel

@Composable
fun AnalyticsScreen(modifier: Modifier = Modifier, viewModel: ForkprintViewModel) {
    val analytics by viewModel.analytics.collectAsState()
    Column(
        modifier.then(Modifier.fillMaxSize().padding(20.dp)),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text("Local insights", style = MaterialTheme.typography.headlineMedium)
        InsightCard("Visits", analytics.totalVisits.toString())
        InsightCard("Restaurants", analytics.uniqueRestaurants.toString())
        InsightCard("Average rating", analytics.averageRating?.let { "%.1f / 5".format(it) } ?: "Not enough ratings")
        InsightCard("Most visited", analytics.topRestaurantName ?: "No visits yet")
    }
}

@Composable
private fun InsightCard(label: String, value: String) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)
            Text(value, style = MaterialTheme.typography.titleLarge)
        }
    }
}
