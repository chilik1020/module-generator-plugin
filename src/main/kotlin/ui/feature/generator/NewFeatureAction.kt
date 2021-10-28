package ui.feature.generator

import model.Module
import model.ModuleType


sealed class NewFeatureAction {
    data class OkClicked(
        val newModuleName: String,
        val packageName: String,
        val moduleType: ModuleType,
        val parentModule: Module
    ) : NewFeatureAction()

    data class ModuleTypeIndexChanged(val index: Int) : NewFeatureAction()
    data class ModuleNameChanged(val text: String) : NewFeatureAction()
    data class PackageNameChanged(val text: String) : NewFeatureAction()
    data class ParentModuleChanged(val index: Int) : NewFeatureAction()
}