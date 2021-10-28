package ui.feature.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.feature.base.BaseReducer
import ui.feature.settings.FeatureSettingsEffect
import ui.feature.settings.FeatureSettingsState
import javax.inject.Inject

interface SelectModuleReducer {

    operator fun invoke(index: Int)
}

class SelectModuleReducerImpl @Inject constructor(
    private val state: MutableStateFlow<FeatureSettingsState>,
    effect: MutableSharedFlow<FeatureSettingsEffect>,
    scope: CoroutineScope,
    private val selectFileReducer: SelectFileReducer
) : BaseReducer<FeatureSettingsState, FeatureSettingsEffect>(state, effect, scope), SelectModuleReducer {

    override fun invoke(index: Int) {
        val selectedIndex =
            if (state.value.modules.isEmpty() && index in state.value.modules.indices) {
                index
            } else {
                null
            }

        val selectedModule = selectedIndex?.let { state.value.modules[selectedIndex] }
        val selectedFile = selectedModule?.files?.firstOrNull()

        pushState {
            copy(
                selectedModuleIndex = index
            )
        }
        if (selectedFile != null) {
            selectFileReducer(0)
        } else {
            selectFileReducer(-1)
        }
    }
}