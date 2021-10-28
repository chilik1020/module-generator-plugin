package ui.feature.generator.reducer

import data.file.FileCreator
import data.file.WriteActionDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.Module
import model.ModuleType
import ui.feature.base.BaseReducer
import ui.feature.generator.NewFeatureEffect
import ui.feature.generator.NewFeatureState
import javax.inject.Inject

fun interface OkClickReducer {

    operator fun invoke(
        newModuleName: String,
        packageName: String,
        moduleType: ModuleType,
        parentModule: Module
    )
}

class FeatureOkClickReducer @Inject constructor(
    state: MutableStateFlow<NewFeatureState>,
    effect: MutableSharedFlow<NewFeatureEffect>,
    scope: CoroutineScope,
    private val fileCreator: FileCreator,
    private val writeActionDispatcher: WriteActionDispatcher
) : BaseReducer<NewFeatureState, NewFeatureEffect>(state, effect, scope), OkClickReducer {

    override fun invoke(newModuleName: String, packageName: String, moduleType: ModuleType, parentModule: Module) {
        writeActionDispatcher.dispatch {
            fileCreator.createFeatureFiles(newModuleName, packageName, moduleType, parentModule)
        }
        pushEffect(NewFeatureEffect.Close)
    }
}