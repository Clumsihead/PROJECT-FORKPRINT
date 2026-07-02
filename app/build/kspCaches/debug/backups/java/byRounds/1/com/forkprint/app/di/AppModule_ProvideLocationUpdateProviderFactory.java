package com.forkprint.app.di;

import com.forkprint.app.location.AndroidLocationUpdateProvider;
import com.forkprint.app.location.LocationUpdateProvider;
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
public final class AppModule_ProvideLocationUpdateProviderFactory implements Factory<LocationUpdateProvider> {
  private final Provider<AndroidLocationUpdateProvider> providerProvider;

  public AppModule_ProvideLocationUpdateProviderFactory(
      Provider<AndroidLocationUpdateProvider> providerProvider) {
    this.providerProvider = providerProvider;
  }

  @Override
  public LocationUpdateProvider get() {
    return provideLocationUpdateProvider(providerProvider.get());
  }

  public static AppModule_ProvideLocationUpdateProviderFactory create(
      Provider<AndroidLocationUpdateProvider> providerProvider) {
    return new AppModule_ProvideLocationUpdateProviderFactory(providerProvider);
  }

  public static LocationUpdateProvider provideLocationUpdateProvider(
      AndroidLocationUpdateProvider provider) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideLocationUpdateProvider(provider));
  }
}
