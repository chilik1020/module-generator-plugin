package ui.feature.settings.reducer

import data.repository.FeatureSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.feature.base.BaseReducer
import ui.feature.settings.FeatureSettingsEffect
import ui.feature.settings.FeatureSettingsState
import javax.inject.Inject

interface ResetFeatureSettingsReducer {

    operator fun invoke()
}

class ResetFeatureSettingsReducerImpl @Inject constructor(
    private val state: MutableStateFlow<FeatureSettingsState>,
    effect: MutableSharedFlow<FeatureSettingsEffect>,
    scope: CoroutineScope,
    private val featureSettingsRepository: FeatureSettingsRepository
) : BaseReducer<FeatureSettingsState, FeatureSettingsEffect>(state, effect, scope), ResetFeatureSettingsReducer {

    override fun invoke() {
        val moduleFiles = featureSettingsRepository.loadModuleTypesWithFiles()
        pushState {
            val selectedModule = if (moduleFiles.isNotEmpty()) 0 else null
            copy(
                isModified = false,
                modules = moduleFiles,
                selectedModuleIndex = selectedModule
            )
        }
    }
}