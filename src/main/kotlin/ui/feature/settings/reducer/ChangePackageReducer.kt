package ui.feature.settings.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.feature.base.BaseReducer
import ui.feature.settings.FeatureSettingsEffect
import ui.feature.settings.FeatureSettingsState
import javax.inject.Inject

interface ChangePackageReducer {

    operator fun invoke(text: String)
}

class ChangePackageReducerImpl @Inject constructor(
    private val state: MutableStateFlow<FeatureSettingsState>,
    effect: MutableSharedFlow<FeatureSettingsEffect>,
    scope: CoroutineScope,
) : BaseReducer<FeatureSettingsState, FeatureSettingsEffect>(state, effect, scope), ChangePackageReducer {

    override fun invoke(text: String) = pushState {
        copy(
            basePackage = text,
            isModified = true
        )
    }
}