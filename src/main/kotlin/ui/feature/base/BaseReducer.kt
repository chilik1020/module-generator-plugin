package ui.feature.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class BaseReducer<S, E>(
        private val state: MutableStateFlow<S>,
        private val effect: MutableSharedFlow<E>,
        private val scope: CoroutineScope
) {

    protected fun pushEffect(newEffect: E) {
        scope.launch { effect.emit(newEffect) }
    }

    protected fun pushState(transform: S.() -> S) {
        state.value = transform(state.value)
    }
}


