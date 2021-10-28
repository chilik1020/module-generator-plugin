package ui.feature.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.feature.base.BaseReducer
import ui.feature.settings.FeatureSettingsEffect
import ui.feature.settings.FeatureSettingsState
import javax.inject.Inject

interface ChangeTemplateReducer {

    operator fun invoke(text: String)
}

class ChangeTemplateReducerImpl @Inject constructor(
    private val state: MutableStateFlow<FeatureSettingsState>,
    effect: MutableSharedFlow<FeatureSettingsEffect>,
    scope: CoroutineScope,
    private val updateFileReducer: UpdateFileReducer
) : BaseReducer<FeatureSettingsState, FeatureSettingsEffect>(state, effect, scope), ChangeTemplateReducer {

    override fun invoke(text: String) {
        state.value.selectedFile?.let {
            updateFileReducer(it.copy(content = text))
        }
    }
}