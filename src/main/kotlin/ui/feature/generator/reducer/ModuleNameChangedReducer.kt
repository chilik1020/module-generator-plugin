package ui.feature.generator.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.DEFAULT_PACKAGE
import ui.feature.base.BaseReducer
import ui.feature.generator.NewFeatureEffect
import ui.feature.generator.NewFeatureState
import javax.inject.Inject

interface ModuleNameChangedReducer {

    operator fun invoke(moduleName: String)
}

class ModuleNameChangedReducerImpl @Inject constructor(
    state: MutableStateFlow<NewFeatureState>,
    effect: MutableSharedFlow<NewFeatureEffect>,
    scope: CoroutineScope
) : BaseReducer<NewFeatureState, NewFeatureEffect>(state, effect, scope), ModuleNameChangedReducer {


    override fun invoke(moduleName: String) = pushState {
        val packageName = selectedProjectModule?.let {
            if (it.nameWithoutPrefix.isEmpty()) {
                "$DEFAULT_PACKAGE.$moduleName"
            } else {
                "$DEFAULT_PACKAGE.${it.nameWithoutPrefix}.$moduleName"
            }
        } ?: "$DEFAULT_PACKAGE.$moduleName"
        copy(
            moduleName = moduleName,
            packageName = packageName
        )
    }
}