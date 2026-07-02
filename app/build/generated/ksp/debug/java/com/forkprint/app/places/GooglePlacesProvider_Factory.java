package com.forkprint.app.places;

import android.content.Context;
import com.forkprint.app.data.local.dao.CachedPlaceDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class GooglePlacesProvider_Factory implements Factory<GooglePlacesProvider> {
  private final Provider<Context> contextProvider;

  private final Provider<CachedPlaceDao> cachedPlaceDaoProvider;

  public GooglePlacesProvider_Factory(Provider<Context> contextProvider,
      Provider<CachedPlaceDao> cachedPlaceDaoProvider) {
    this.contextProvider = contextProvider;
    this.cachedPlaceDaoProvider = cachedPlaceDaoProvider;
  }

  @Override
  public GooglePlacesProvider get() {
    return newInstance(contextProvider.get(), cachedPlaceDaoProvider.get());
  }

  public static GooglePlacesProvider_Factory create(Provider<Context> contextProvider,
      Provider<CachedPlaceDao> cachedPlaceDaoProvider) {
    return new GooglePlacesProvider_Factory(contextProvider, cachedPlaceDaoProvider);
  }

  public static GooglePlacesProvider newInstance(Context context, CachedPlaceDao cachedPlaceDao) {
    return new GooglePlacesProvider(context, cachedPlaceDao);
  }
}
