package com.forkprint.app.di;

import com.forkprint.app.places.GooglePlacesProvider;
import com.forkprint.app.places.PlacesProvider;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvidePlacesProviderFactory implements Factory<PlacesProvider> {
  private final Provider<GooglePlacesProvider> providerProvider;

  public AppModule_ProvidePlacesProviderFactory(Provider<GooglePlacesProvider> providerProvider) {
    this.providerProvider = providerProvider;
  }

  @Override
  public PlacesProvider get() {
    return providePlacesProvider(providerProvider.get());
  }

  public static AppModule_ProvidePlacesProviderFactory create(
      Provider<GooglePlacesProvider> providerProvider) {
    return new AppModule_ProvidePlacesProviderFactory(providerProvider);
  }

  public static PlacesProvider providePlacesProvider(GooglePlacesProvider provider) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.providePlacesProvider(provider));
  }
}
