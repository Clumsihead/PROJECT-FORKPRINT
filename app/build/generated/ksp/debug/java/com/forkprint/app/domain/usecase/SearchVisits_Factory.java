package com.forkprint.app.domain.usecase;

import com.forkprint.app.domain.repository.VisitRepository;
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
public final class SearchVisits_Factory implements Factory<SearchVisits> {
  private final Provider<VisitRepository> repositoryProvider;

  public SearchVisits_Factory(Provider<VisitRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SearchVisits get() {
    return newInstance(repositoryProvider.get());
  }

  public static SearchVisits_Factory create(Provider<VisitRepository> repositoryProvider) {
    return new SearchVisits_Factory(repositoryProvider);
  }

  public static SearchVisits newInstance(VisitRepository repository) {
    return new SearchVisits(repository);
  }
}
