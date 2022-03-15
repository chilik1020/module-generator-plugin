package ui.feature.generator

import model.Module
import model.ModuleType

data class NewFeatureState(
    val moduleName: String = String(),
    val packageName: String = String(),
    val modulesTypes: List<ModuleType> = emptyList(),
    val selectedModuleType: ModuleType? = null,
    val projectModules: List<Module> = emptyList(),
    val selectedProjectModule: Module? = null,
    val kmmGatewaySubModuleName: String = String(),
    val kmmDomainSubModuleName: String = String(),
    val kmmPresentationSubModuleName: String = String()
)