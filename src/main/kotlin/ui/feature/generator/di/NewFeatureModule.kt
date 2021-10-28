package ui.feature.generator.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import data.file.*
import data.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.feature.generator.NewFeatureEffect
import ui.feature.generator.NewFeatureState
import ui.feature.generator.reducer.*
import javax.inject.Singleton

@Module
abstract class NewFeatureModule {

    @Binds
    abstract fun bindProjectStructure(projectStructure: ProjectStructureImpl): ProjectStructure

    @Binds
    abstract fun bindSourceRootRepository(repository: SourceRootRepositoryImpl): SourceRootRepository

    @Binds
    abstract fun bindFeatureSettingsRepository(repository: FeatureSettingsRepositoryImpl): FeatureSettingsRepository

    @Binds
    abstract fun bindPackageExtractor(extractor: PackageExtractorImpl): PackageExtractor

    @Binds
    abstract fun bindModuleRepository(repository: ModuleRepositoryImpl): ModuleRepository

    @Binds
    abstract fun bindFileCreator(fileCreator: FileCreatorImpl): FileCreator

    @Binds
    abstract fun bindWriteActionDispatcher(dispatcher: WriteActionDispatcherImpl): WriteActionDispatcher

    @Binds
    abstract fun bindInitReducer(reducer: FeatureInitReducer): InitReducer

    @Binds
    abstract fun bindOkClickReducer(reducer: FeatureOkClickReducer): OkClickReducer

    @Binds
    abstract fun bindModuleTypeIndexChangedReducer(reducer: ModuleTypeIndexChangedReducerImpl): ModuleTypeIndexChangedReducer

    @Binds
    abstract fun bindModuleNameChangedReducer(reducer: ModuleNameChangedReducerImpl): ModuleNameChangedReducer

    @Binds
    abstract fun bindPackageNameChangedReducer(reducer: PackageNameChangedReducerImpl): PackageNameChangedReducer

    @Binds
    abstract fun bindParentModuleChangedReducer(reducer: ParentModuleIndexChangedReducerImpl): ParentModuleIndexChangedReducer

    companion object {

        @Provides
        @Singleton
        fun provideState() = MutableStateFlow(NewFeatureState())

        @Provides
        @Singleton
        fun provideEffect() = MutableSharedFlow<NewFeatureEffect>(replay = 0)

        @Provides
        @Singleton
        fun providesScope(): CoroutineScope = MainScope()
    }
}