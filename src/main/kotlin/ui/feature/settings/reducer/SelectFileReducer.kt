package ui.feature.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.feature.base.BaseReducer
import ui.feature.settings.FeatureSettingsEffect
import ui.feature.settings.FeatureSettingsState
import javax.inject.Inject

interface SelectFileReducer {

    operator fun invoke(index: Int)
}

class SelectFileReducerImpl @Inject constructor(
    private val state: MutableStateFlow<FeatureSettingsState>,
    effect: MutableSharedFlow<FeatureSettingsEffect>,
    scope: CoroutineScope
) : BaseReducer<FeatureSettingsState, FeatureSettingsEffect>(state, effect, scope), SelectFileReducer {

    override fun invoke(index: Int) {
        pushState {
            val files = selectedModuleIndex?.let { modules[it].files } ?: emptyList()
            val selectedIndex =
                if (files.isNotEmpty() && index in files.indices) {
                    index
                } else {
                    null
                }

            val file = selectedIndex?.let { files[it] }
            val sampleCode = file?.content ?: "null"

            copy(
                selectedFileIndex = selectedIndex,
                sampleCode = sampleCode
            )
        }
    }
}