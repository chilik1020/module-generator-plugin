package ui.feature.generator.reducer

import data.repository.FeatureSettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.*
import ui.feature.base.BaseReducer
import ui.feature.generator.NewFeatureEffect
import ui.feature.generator.NewFeatureState
import util.buildPackage
import util.packagePrefixByModuleType
import javax.inject.Inject

interface ModuleNameChangedReducer {

    operator fun invoke(moduleName: String)
}

class ModuleNameChangedReducerImpl @Inject constructor(
    state: MutableStateFlow<NewFeatureState>,
    effect: MutableSharedFlow<NewFeatureEffect>,
    scope: CoroutineScope,
    private val featureSettings: FeatureSettingsRepository
) : BaseReducer<NewFeatureState, NewFeatureEffect>(state, effect, scope), ModuleNameChangedReducer {


    override fun invoke(moduleName: String) = pushState {
        val selectedModuleType = selectedModuleType ?: modulesTypes[0]
        val packagePrefix = packagePrefixByModuleType(selectedModuleType, featureSettings)
        val basePackage = buildPackage(selectedModuleType, this, moduleName, packagePrefix)
        val kmmSubmodulesPrefix = featureSettings.loadDefaultKmmFeatureSubmodulePrefix()
        copy(
            moduleName = moduleName,
            packageName = basePackage,
            kmmGatewaySubModuleName = "${kmmSubmodulesPrefix}-$moduleName-$GATEWAY_POSTFIX",
            kmmDomainSubModuleName = "${kmmSubmodulesPrefix}-$moduleName-$DOMAIN_POSTFIX",
            kmmPresentationSubModuleName = "${kmmSubmodulesPrefix}-$moduleName-$PRESENTATION_POSTFIX"
        )
    }
}