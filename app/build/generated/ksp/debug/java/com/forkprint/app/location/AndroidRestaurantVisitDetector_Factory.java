package com.forkprint.app.location;

import com.forkprint.app.places.PlacesProvider;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class AndroidRestaurantVisitDetector_Factory implements Factory<AndroidRestaurantVisitDetector> {
  private final Provider<LocationUpdateProvider> locationUpdateProvider;

  private final Provider<PlacesProvider> placesProvider;

  public AndroidRestaurantVisitDetector_Factory(
      Provider<LocationUpdateProvider> locationUpdateProvider,
      Provider<PlacesProvider> placesProvider) {
    this.locationUpdateProvider = locationUpdateProvider;
    this.placesProvider = placesProvider;
  }

  @Override
  public AndroidRestaurantVisitDetector get() {
    return newInstance(locationUpdateProvider.get(), placesProvider.get());
  }

  public static AndroidRestaurantVisitDetector_Factory create(
      Provider<LocationUpdateProvider> locationUpdateProvider,
      Provider<PlacesProvider> placesProvider) {
    return new AndroidRestaurantVisitDetector_Factory(locationUpdateProvider, placesProvider);
  }

  public static AndroidRestaurantVisitDetector newInstance(
      LocationUpdateProvider locationUpdateProvider, PlacesProvider placesProvider) {
    return new AndroidRestaurantVisitDetector(locationUpdateProvider, placesProvider);
  }
}
