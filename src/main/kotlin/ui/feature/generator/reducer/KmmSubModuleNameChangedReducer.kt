package ui.feature.generator.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.feature.base.BaseReducer
import ui.feature.generator.NewFeatureEffect
import ui.feature.generator.NewFeatureState
import javax.inject.Inject

interface KmmGatewayNameChangedReducer {

    operator fun invoke(text: String)
}

interface KmmDomainNameChangedReducer {

    operator fun invoke(text: String)
}

interface KmmPresentationNameChangedReducer {

    operator fun invoke(text: String)
}

class KmmGatewayNameChangedReducerImpl @Inject constructor(
    state: MutableStateFlow<NewFeatureState>,
    effect: MutableSharedFlow<NewFeatureEffect>,
    scope: CoroutineScope
) : BaseReducer<NewFeatureState, NewFeatureEffect>(state, effect, scope), KmmGatewayNameChangedReducer {

    override fun invoke(text: String) = pushState {
        copy(
            kmmGatewaySubModuleName = text
        )
    }
}

class KmmDomainNameChangedReducerImpl @Inject constructor(
    state: MutableStateFlow<NewFeatureState>,
    effect: MutableSharedFlow<NewFeatureEffect>,
    scope: CoroutineScope
) : BaseReducer<NewFeatureState, NewFeatureEffect>(state, effect, scope), KmmDomainNameChangedReducer {

    override fun invoke(text: String) = pushState {
        copy(
            kmmDomainSubModuleName = text
        )
    }
}

class KmmPresentationNameChangedReducerImpl @Inject constructor(
    state: MutableStateFlow<NewFeatureState>,
    effect: MutableSharedFlow<NewFeatureEffect>,
    scope: CoroutineScope
) : BaseReducer<NewFeatureState, NewFeatureEffect>(state, effect, scope), KmmPresentationNameChangedReducer {

    override fun invoke(text: String) = pushState {
        copy(
            kmmGatewaySubModuleName = text
        )
    }
}