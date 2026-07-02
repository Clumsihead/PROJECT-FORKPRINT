package com.forkprint.app.ui;

import com.forkprint.app.domain.repository.VisitRepository;
import com.forkprint.app.location.RestaurantVisitDetector;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ForkprintViewModel_Factory implements Factory<ForkprintViewModel> {
  private final Provider<VisitRepository> repositoryProvider;

  private final Provider<RestaurantVisitDetector> visitDetectorProvider;

  public ForkprintViewModel_Factory(Provider<VisitRepository> repositoryProvider,
      Provider<RestaurantVisitDetector> visitDetectorProvider) {
    this.repositoryProvider = repositoryProvider;
    this.visitDetectorProvider = visitDetectorProvider;
  }

  @Override
  public ForkprintViewModel get() {
    return newInstance(repositoryProvider.get(), visitDetectorProvider.get());
  }

  public static ForkprintViewModel_Factory create(Provider<VisitRepository> repositoryProvider,
      Provider<RestaurantVisitDetector> visitDetectorProvider) {
    return new ForkprintViewModel_Factory(repositoryProvider, visitDetectorProvider);
  }

  public static ForkprintViewModel newInstance(VisitRepository repository,
      RestaurantVisitDetector visitDetector) {
    return new ForkprintViewModel(repository, visitDetector);
  }
}
