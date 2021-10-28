package ui.feature.settings.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import data.repository.FeatureSettingsRepository
import data.repository.FeatureSettingsRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.feature.settings.FeatureSettingsEffect
import ui.feature.settings.FeatureSettingsState
import ui.feature.settings.reducer.*
import javax.inject.Singleton

@Module
abstract class FeatureSettingsModule {

    @Binds
    abstract fun bindFeatureSettingsRepository(repository: FeatureSettingsRepositoryImpl): FeatureSettingsRepository

    @Binds
    abstract fun bindApplyFeatureSettingsReducer(reducer: ApplyFeatureSettingReducerImpl): ApplyFeatureSettingsReducer

    @Binds
    abstract fun bindResetFeatureSettingsReducer(reducer: ResetFeatureSettingsReducerImpl): ResetFeatureSettingsReducer

    @Binds
    abstract fun bindSelectModuleReducer(reducer: SelectModuleReducerImpl): SelectModuleReducer

    @Binds
    abstract fun bindSelectFileReducer(reducer: SelectFileReducerImpl): SelectFileReducer

    @Binds
    abstract fun bindChangeTemplateReducer(reducerImpl: ChangeTemplateReducerImpl): ChangeTemplateReducer

    @Binds
    abstract fun bindUpdateFileReducer(reducer: UpdateFileReducerImpl): UpdateFileReducer

    companion object {

        @Provides
        @Singleton
        fun provideState() = MutableStateFlow(FeatureSettingsState())

        @Provides
        @Singleton
        fun provideEffect() = MutableSharedFlow<FeatureSettingsEffect>(replay = 0)

        @Provides
        @Singleton
        fun provideScope(): CoroutineScope = MainScope()
    }
}