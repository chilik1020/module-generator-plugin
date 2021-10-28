package ui.feature.generator.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.DEFAULT_PACKAGE
import ui.feature.base.BaseReducer
import ui.feature.generator.NewFeatureEffect
import ui.feature.generator.NewFeatureState
import javax.inject.Inject

interface ParentModuleIndexChangedReducer {

    operator fun invoke(index: Int)
}

class ParentModuleIndexChangedReducerImpl @Inject constructor(
    state: MutableStateFlow<NewFeatureState>,
    effect: MutableSharedFlow<NewFeatureEffect>,
    scope: CoroutineScope
) : BaseReducer<NewFeatureState, NewFeatureEffect>(state, effect, scope), ParentModuleIndexChangedReducer {

    override fun invoke(index: Int) = pushState {
        val selectedModule = projectModules[index]
        val packageName = if(selectedModule.nameWithoutPrefix.isEmpty()) {
            "$DEFAULT_PACKAGE.$moduleName"
        } else {
            "$DEFAULT_PACKAGE.${selectedModule.nameWithoutPrefix}.$moduleName"
        }
        copy(
            selectedProjectModule = selectedModule,
            packageName = packageName
        )
    }
}