package ui.feature.settings.reducer

import data.repository.FeatureSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.FeatureSettings
import ui.feature.base.BaseReducer
import ui.feature.settings.FeatureSettingsEffect
import ui.feature.settings.FeatureSettingsState
import javax.inject.Inject

interface ApplyFeatureSettingsReducer {

    operator fun invoke()
}

class ApplyFeatureSettingReducerImpl @Inject constructor(
    private val state: MutableStateFlow<FeatureSettingsState>,
    effect: MutableSharedFlow<FeatureSettingsEffect>,
    scope: CoroutineScope,
    private val featureSettingsRepository: FeatureSettingsRepository
) : BaseReducer<FeatureSettingsState, FeatureSettingsEffect>(state, effect, scope), ApplyFeatureSettingsReducer {

    override fun invoke() {
        println("Apply settings")
        val newSettings = state.value.run {
            FeatureSettings(
                modules = modules.map { it.moduleType }.toMutableList(),
                files = modules.flatMap { it.files }.toMutableList()
            )
        }
        featureSettingsRepository.update(newSettings)
        pushState { copy(isModified = false) }
    }
}