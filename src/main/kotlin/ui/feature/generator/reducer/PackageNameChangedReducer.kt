package ui.feature.generator.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.feature.base.BaseReducer
import ui.feature.generator.NewFeatureEffect
import ui.feature.generator.NewFeatureState
import javax.inject.Inject

interface PackageNameChangedReducer {

    operator fun invoke(text: String)
}

class PackageNameChangedReducerImpl @Inject constructor(
    state: MutableStateFlow<NewFeatureState>,
    effect: MutableSharedFlow<NewFeatureEffect>,
    scope: CoroutineScope
) : BaseReducer<NewFeatureState, NewFeatureEffect>(state, effect, scope), PackageNameChangedReducer {

    override fun invoke(text: String) = pushState {
        copy(
            packageName = text
        )
    }
}