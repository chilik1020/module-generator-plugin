package ui.feature.generator.reducer

import data.repository.FeatureSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.ModuleType
import ui.feature.base.BaseReducer
import ui.feature.generator.NewFeatureEffect
import ui.feature.generator.NewFeatureState
import util.buildPackage
import javax.inject.Inject

interface ParentModuleIndexChangedReducer {

    operator fun invoke(index: Int)
}

class ParentModuleIndexChangedReducerImpl @Inject constructor(
    state: MutableStateFlow<NewFeatureState>,
    effect: MutableSharedFlow<NewFeatureEffect>,
    scope: CoroutineScope,
    private val featureSettings: FeatureSettingsRepository
) : BaseReducer<NewFeatureState, NewFeatureEffect>(state, effect, scope), ParentModuleIndexChangedReducer {

    override fun invoke(index: Int) = pushState {
        val selectedModule = projectModules[index]
        val selectedModuleType = selectedModuleType ?: modulesTypes[0]
        val packagePrefix = when (selectedModuleType) {
            ModuleType.ANDROID_MODULE -> featureSettings.loadDefaultPackage()
            ModuleType.FEATURE -> featureSettings.loadDefaultPackage()
            ModuleType.KMM_MODULE -> featureSettings.loadDefaultKmmPackage()
            ModuleType.KMM_FEATURE -> featureSettings.loadDefaultKmmPackage()
            else -> featureSettings.loadDefaultPackage()
        }
        val basePackage = buildPackage(selectedModuleType, this, this.moduleName,packagePrefix)
        copy(
            selectedProjectModule = selectedModule,
            packageName = basePackage
        )
    }
}