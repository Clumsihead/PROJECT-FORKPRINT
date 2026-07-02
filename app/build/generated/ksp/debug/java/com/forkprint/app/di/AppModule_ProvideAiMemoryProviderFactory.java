package com.forkprint.app.di;

import com.forkprint.app.ai.AiMemoryProvider;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class AppModule_ProvideAiMemoryProviderFactory implements Factory<AiMemoryProvider> {
  @Override
  public AiMemoryProvider get() {
    return provideAiMemoryProvider();
  }

  public static AppModule_ProvideAiMemoryProviderFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AiMemoryProvider provideAiMemoryProvider() {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAiMemoryProvider());
  }

  private static final class InstanceHolder {
    private static final AppModule_ProvideAiMemoryProviderFactory INSTANCE = new AppModule_ProvideAiMemoryProviderFactory();
  }
}
