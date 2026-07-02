package com.forkprint.app.di;

import com.forkprint.app.location.AndroidRestaurantVisitDetector;
import com.forkprint.app.location.RestaurantVisitDetector;
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
public final class AppModule_ProvideVisitDetectorFactory implements Factory<RestaurantVisitDetector> {
  private final Provider<AndroidRestaurantVisitDetector> detectorProvider;

  public AppModule_ProvideVisitDetectorFactory(
      Provider<AndroidRestaurantVisitDetector> detectorProvider) {
    this.detectorProvider = detectorProvider;
  }

  @Override
  public RestaurantVisitDetector get() {
    return provideVisitDetector(detectorProvider.get());
  }

  public static AppModule_ProvideVisitDetectorFactory create(
      Provider<AndroidRestaurantVisitDetector> detectorProvider) {
    return new AppModule_ProvideVisitDetectorFactory(detectorProvider);
  }

  public static RestaurantVisitDetector provideVisitDetector(
      AndroidRestaurantVisitDetector detector) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideVisitDetector(detector));
  }
}
