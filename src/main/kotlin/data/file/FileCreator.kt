package data.file

import com.intellij.openapi.fileEditor.FileDocumentManager
import data.repository.FeatureSettingsRepository
import data.repository.SourceRootRepository
import model.*
import ui.feature.generator.NewFeatureState
import javax.inject.Inject

interface FileCreator {

    fun createFeatureFiles(
        state: NewFeatureState,
        newModuleName: String,
        packageName: String,
        moduleType: ModuleType,
        parentModule: Module
    )
}

class FileCreatorImpl @Inject constructor(
    private val featureSettingsRepository: FeatureSettingsRepository,
    private val sourceRootRepository: SourceRootRepository
) : FileCreator {

    override fun createFeatureFiles(
        state: NewFeatureState,
        newModuleName: String,
        packageName: String,
        moduleType: ModuleType,
        parentModule: Module
    ) {
        val parentModuleRootDirectory = findModuleRootDirectory(state.selectedProjectModule!!) ?: findProjectRootDirectory()

        when (moduleType) {
            ModuleType.ANDROID_MODULE -> createSimpleModuleFiles(
                state.moduleName,
                state.selectedModuleType!!,
                state.packageName,
                parentModuleRootDirectory!!
            )
            ModuleType.FEATURE -> createSimpleFeatureFiles(
                state.moduleName,
                state.packageName,
                state.selectedProjectModule,
                parentModuleRootDirectory!!
            )
            ModuleType.KMM_MODULE -> createKmmModuleFiles(
                state.moduleName,
                state.selectedModuleType!!,
                state.packageName,
                parentModuleRootDirectory!!
            )
            ModuleType.KMM_FEATURE -> createKmmFeatureFiles(
                state.moduleName,
                state.packageName,
                state.kmmGatewaySubModuleName,
                state.kmmDomainSubModuleName,
                state.kmmPresentationSubModuleName,
                state.selectedProjectModule,
                parentModuleRootDirectory!!
            )
        }

        val fullModuleName =
            if (state.selectedProjectModule.nameWithoutPrefix.isEmpty()) String() else ":${state.selectedProjectModule.nameWithoutPrefix}"
        editSettingsGradleFile(state.moduleName, fullModuleName)
    }

    private fun createSimpleFeatureFiles(
        newModuleName: String,
        packageName: String,
        parentModule: Module,
        parentDirectory: Directory
    ) {
        val featureRootDirectory = parentDirectory.run { createSubdirectory(newModuleName) }
        createSimpleModuleFiles(
            ModuleType.DOMAIN.title,
            ModuleType.DOMAIN,
            "$packageName.${ModuleType.DOMAIN.title}",
            featureRootDirectory!!
        )

        createSimpleModuleFiles(
            ModuleType.PRESENTATION.title,
            ModuleType.PRESENTATION,
            "$packageName.${ModuleType.PRESENTATION.title}",
            featureRootDirectory
        )
        val fullModuleName = if (parentModule.nameWithoutPrefix.isEmpty()) {
            ":$newModuleName"

        } else {
            ":${parentModule.nameWithoutPrefix}:$newModuleName"
        }
        editSettingsGradleFile(ModuleType.DOMAIN.title, fullModuleName)
        editSettingsGradleFile(ModuleType.PRESENTATION.title, fullModuleName)
    }

    private fun createKmmFeatureFiles(
        newModuleName: String,
        packageName: String,
        gatewaySubModuleName: String,
        domainSubModuleName: String,
        presentationSubModuleName: String,
        parentModule: Module,
        parentDirectory: Directory
    ) {
        val featureRootDirectory = parentDirectory.run { createSubdirectory(newModuleName) }

        createKmmModuleFiles(
            gatewaySubModuleName,
            ModuleType.KMM_GATEWAY,
            "$packageName.$GATEWAY_POSTFIX",
            featureRootDirectory!!
        )

        createKmmModuleFiles(
            domainSubModuleName,
            ModuleType.KMM_DOMAIN,
            "$packageName.$DOMAIN_POSTFIX",
            featureRootDirectory!!
        )

        createKmmModuleFiles(
            presentationSubModuleName,
            ModuleType.KMM_PRESENTATION,
            "$packageName.$PRESENTATION_POSTFIX",
            featureRootDirectory
        )
        val fullModuleName = if (parentModule.nameWithoutPrefix.isEmpty()) {
            ":$newModuleName"
        } else {
            ":${parentModule.nameWithoutPrefix}:$newModuleName"
        }
        editSettingsGradleFile(gatewaySubModuleName, fullModuleName)
        editSettingsGradleFile(domainSubModuleName, fullModuleName)
        editSettingsGradleFile(presentationSubModuleName, fullModuleName)
    }

    private fun createSimpleModuleFiles(
        moduleName: String,
        moduleType: ModuleType,
        packageName: String,
        parentDirectory: Directory
    ) {
        val moduleDirectory = parentDirectory.createSubdirectory(moduleName)
        val srcDir = moduleDirectory.createSubdirectory(FOLDER_SRC_NAME)
        val mainDir = srcDir.createSubdirectory(FOLDER_MAIN_NAME)
        val kotlinDir = mainDir.createSubdirectory(FOLDER_KOTLIN_NAME)
        val packageDirs = packageName.split('.').toMutableList()
        var topPackageDir = kotlinDir
        packageDirs.forEach {
            topPackageDir = topPackageDir.createSubdirectory(it)
        }
        featureSettingsRepository.loadModuleFiles(moduleType).forEach {
            when (it.fileType) {
                FileType.MANIFEST -> {
                    it.content = it.content.replace(Variable.PACKAGE_NAME.value, packageName)
                    moduleDirectory.addFile(it)
                }
                FileType.GRADLE -> moduleDirectory.addFile(it)
                FileType.GIT_IGNORE -> moduleDirectory.addFile(it)
                FileType.PROGUARD -> moduleDirectory.addFile(it)
                FileType.KOTLIN -> topPackageDir.addFile(it)
                else -> {
                }
            }
        }
    }

    private fun createKmmModuleFiles(
        moduleName: String,
        moduleType: ModuleType,
        packageName: String,
        parentDirectory: Directory
    ) {
        val moduleDirectory = parentDirectory.createSubdirectory(moduleName)
        val srcDir = moduleDirectory.createSubdirectory(FOLDER_SRC_NAME)
        val androidMain = srcDir.createSubdirectory(FOLDER_ANDROID_MAIN_NAME)
        val commonMain = srcDir.createSubdirectory(FOLDER_COMMON_MAIN_NAME)
        val iosMain = srcDir.createSubdirectory(FOLDER_IOS_MAIN_NAME)


        val androidTopDir = createPackageDirsHierarchy(androidMain, packageName)
        val commonTopDir = createPackageDirsHierarchy(commonMain, packageName)
        val iosTopDir = createPackageDirsHierarchy(iosMain, packageName)

        featureSettingsRepository.loadModuleFiles(moduleType).forEach {
            when (it.fileType) {
                FileType.MANIFEST -> {
                    it.content = it.content.replace(Variable.PACKAGE_NAME.value, packageName)
                    androidMain.addFile(it)
                }
                FileType.GRADLE -> moduleDirectory.addFile(it)
                FileType.GRADLE_DSL -> moduleDirectory.addFile(it)
                FileType.KOTLIN -> commonTopDir.addFile(it)
                else -> {
                }
            }
        }
    }

    private fun createPackageDirsHierarchy(topDirectory: Directory, packagePath: String): Directory {
        val kotlinDir = topDirectory.createSubdirectory(FOLDER_KOTLIN_NAME)
        val packageDirs = packagePath.split('.').toMutableList()
        var topPackageDir = kotlinDir
        packageDirs.forEach {
            topPackageDir = topPackageDir.createSubdirectory(it)
        }

        return topPackageDir
    }

    private fun findModuleRootDirectory(module: Module) =
        sourceRootRepository.findModuleSourceRoot(module)?.directory

    private fun findProjectRootDirectory() =
        sourceRootRepository.getProjectRoot()?.directory

    private fun editSettingsGradleFile(featureName: String, moduleName: String) {
        sourceRootRepository.getSettingsGradleFile()?.run {
            FileDocumentManager.getInstance().getDocument(this.virtualFile)?.let {
                val textLength = it.textLength
                it.insertString(
                    textLength,
                    "\n$INCLUDE '${moduleName.replace('.', ':')}:$featureName'"
                )
            }
        }
    }
}
