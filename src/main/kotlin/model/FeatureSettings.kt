package model

import data.file.File
import java.io.Serializable

data class FeatureSettings(
    var modules: MutableList<ModuleType> = defaultModuleTypes(),
    var files: MutableList<File> = generateDefaultFiles(),
    var defaultPackage: String = DEFAULT_PACKAGE,
    var defaultKmmPackage: String = KMM_DEFAULT_PACKAGE,
    var defaultKmmFeatureSubmodulePrefix: String = KMM_SUB_MODULE_DEFAULT_NAME_TEMPLATE
) : Serializable

private fun defaultModuleTypes() = mutableListOf<ModuleType>().apply {
    ModuleType.values().forEach { add(it) }
}

private fun generateDefaultFiles() = mutableListOf(
    File(
        name = GRADLE_BUILD_FILE_NAME,
        content = DEFAULT_GRADLE_TEMPLATE,
        fileType = FileType.GRADLE
    ),
    File(
        name = PRO_GUARD_FILE_NAME,
        content = PRO_GUARD_TEMPLATE,
        fileType = FileType.PROGUARD
    ),
    File(
        name = GIT_IGNORE_FILE_NAME,
        content = GIT_IGNORE_TEMPLATE,
        fileType = FileType.GIT_IGNORE
    ),
    File(
        name = ANDROID_MANIFEST_FILE_NAME,
        content = MANIFEST_TEMPLATE,
        fileType = FileType.MANIFEST
    ),

    File(
        moduleType = ModuleType.DOMAIN,
        name = GRADLE_BUILD_FILE_NAME,
        content = DOMAIN_GRADLE_TEMPLATE,
        fileType = FileType.GRADLE
    ),
    File(
        moduleType = ModuleType.DOMAIN,
        name = PRO_GUARD_FILE_NAME,
        content = PRO_GUARD_TEMPLATE,
        fileType = FileType.PROGUARD
    ),
    File(
        moduleType = ModuleType.DOMAIN,
        name = GIT_IGNORE_FILE_NAME,
        content = GIT_IGNORE_TEMPLATE,
        fileType = FileType.GIT_IGNORE
    ),

    File(
        moduleType = ModuleType.PRESENTATION,
        name = GRADLE_BUILD_FILE_NAME,
        content = PRESENTATION_GRADLE_TEMPLATE,
        fileType = FileType.GRADLE
    ),
    File(
        moduleType = ModuleType.PRESENTATION,
        name = PRO_GUARD_FILE_NAME,
        content = PRO_GUARD_TEMPLATE,
        fileType = FileType.PROGUARD
    ),
    File(
        moduleType = ModuleType.PRESENTATION,
        name = GIT_IGNORE_FILE_NAME,
        content = GIT_IGNORE_TEMPLATE,
        fileType = FileType.GIT_IGNORE
    ),
    File(
        moduleType = ModuleType.PRESENTATION,
        name = ANDROID_MANIFEST_FILE_NAME,
        content = MANIFEST_TEMPLATE,
        fileType = FileType.MANIFEST
    ),
    File(
        moduleType = ModuleType.KMM_MODULE,
        name = ANDROID_MANIFEST_FILE_NAME,
        content = MANIFEST_TEMPLATE,
        fileType = FileType.MANIFEST
    ),
    File(
        moduleType = ModuleType.KMM_MODULE,
        name = GRADLE_BUILD_FILE_NAME,
        content = DEFAULT_GRADLE_DSL_TEMPLATE,
        fileType = FileType.GRADLE_DSL
    ),
    File(
        moduleType = ModuleType.KMM_GATEWAY,
        name = ANDROID_MANIFEST_FILE_NAME,
        content = MANIFEST_TEMPLATE,
        fileType = FileType.MANIFEST
    ),
    File(
        moduleType = ModuleType.KMM_GATEWAY,
        name = GRADLE_BUILD_FILE_NAME,
        content = DEFAULT_GRADLE_DSL_TEMPLATE,
        fileType = FileType.GRADLE_DSL
    ),
    File(
        moduleType = ModuleType.KMM_DOMAIN,
        name = ANDROID_MANIFEST_FILE_NAME,
        content = MANIFEST_TEMPLATE,
        fileType = FileType.MANIFEST
    ),
    File(
        moduleType = ModuleType.KMM_DOMAIN,
        name = GRADLE_BUILD_FILE_NAME,
        content = DEFAULT_GRADLE_DSL_TEMPLATE,
        fileType = FileType.GRADLE_DSL
    ),
    File(
        moduleType = ModuleType.KMM_PRESENTATION,
        name = ANDROID_MANIFEST_FILE_NAME,
        content = MANIFEST_TEMPLATE,
        fileType = FileType.MANIFEST
    ),
    File(
        moduleType = ModuleType.KMM_PRESENTATION,
        name = GRADLE_BUILD_FILE_NAME,
        content = DEFAULT_GRADLE_DSL_TEMPLATE,
        fileType = FileType.GRADLE_DSL
    )
)