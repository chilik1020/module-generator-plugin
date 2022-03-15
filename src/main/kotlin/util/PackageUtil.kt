package util

import model.ModuleType
import ui.feature.generator.NewFeatureState

fun buildPackage(moduleType: ModuleType, state: NewFeatureState, moduleName: String, packagePrefix: String) =
        when (moduleType) {
            ModuleType.ANDROID_MODULE -> simpleModulePackage(state, moduleName, packagePrefix)
            ModuleType.FEATURE -> simpleModulePackage(state, moduleName, packagePrefix)
            ModuleType.KMM_MODULE -> kmmModulePackage(state, moduleName, packagePrefix)
            ModuleType.KMM_FEATURE -> kmmModulePackage(state, moduleName, packagePrefix)
            else -> packagePrefix
        }.replace('-', '.')

private fun simpleModulePackage(state: NewFeatureState, moduleName: String, packagePrefix: String) = state.selectedProjectModule?.let {
    if (it.nameWithoutPrefix.isEmpty()) {
        "$packagePrefix.$moduleName"
    } else {
        "$packagePrefix.${it.nameWithoutPrefix}.$moduleName"
    }
} ?: "$packagePrefix.$moduleName"

private fun kmmModulePackage(state: NewFeatureState, moduleName: String, packagePrefix: String) = state.selectedProjectModule?.let {
    if (it.nameWithoutPrefix.isEmpty()) {
        "$packagePrefix.$moduleName"
    } else {
        "$packagePrefix.${it.nameWithoutPrefix}.$moduleName"
    }
} ?: "$packagePrefix.$moduleName"