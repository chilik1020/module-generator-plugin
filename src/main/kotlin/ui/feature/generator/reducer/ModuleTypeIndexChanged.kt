package ui.feature.generator.reducer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.DEFAULT_PACKAGE
import model.ModuleType
import ui.feature.base.BaseReducer
import ui.feature.generator.NewFeatureEffect
import ui.feature.generator.NewFeatureState
import javax.inject.Inject

interface ModuleTypeIndexChangedReducer {

    operator fun invoke(index: Int)
}

class ModuleTypeIndexChangedReducerImpl @Inject constructor(
    state: MutableStateFlow<NewFeatureState>,
    effect: MutableSharedFlow<NewFeatureEffect>,
    scope: CoroutineScope
) : BaseReducer<NewFeatureState, NewFeatureEffect>(state, effect, scope), ModuleTypeIndexChangedReducer {

    override fun invoke(index: Int) = pushState {
        val moduleName = when(modulesTypes[index]) {
            ModuleType.DEFAULT -> String()
            ModuleType.DOMAIN -> ModuleType.DOMAIN.title
            ModuleType.PRESENTATION -> ModuleType.PRESENTATION.title
            ModuleType.FEATURE -> String()
        }
        println("Selected module $selectedProjectModule")
        val packageName = selectedProjectModule?.let {
            if (it.nameWithoutPrefix.isEmpty()) {
                "$DEFAULT_PACKAGE.$moduleName"
            } else {
                "$DEFAULT_PACKAGE.${it.nameWithoutPrefix}.$moduleName"
            }
        } ?: "$DEFAULT_PACKAGE.$moduleName"
        copy(
            selectedModuleType = modulesTypes[index],
            moduleName = moduleName,
            packageName = packageName
        )
    }
}