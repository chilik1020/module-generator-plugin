package data.file

import com.intellij.openapi.fileEditor.FileDocumentManager
import data.repository.FeatureSettingsRepository
import data.repository.SourceRootRepository
import model.FileType
import model.Module
import model.ModuleType
import model.Variable
import javax.inject.Inject

private const val LAYOUT_DIRECTORY = "layout"

interface FileCreator {

    fun createFeatureFiles(
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
        newModuleName: String,
        packageName: String,
        moduleType: ModuleType,
        parentModule: Module
    ) {
        val parentModuleRootDirectory = findModuleRootDirectory(parentModule) ?: findProjectRootDirectory()

        when (moduleType) {
            ModuleType.DEFAULT,
            ModuleType.DOMAIN,
            ModuleType.PRESENTATION -> createSimpleModuleFiles(
                newModuleName,
                moduleType,
                packageName,
                parentModuleRootDirectory!!
            )
            ModuleType.FEATURE -> createSimpleFeatureFiles(
                newModuleName,
                packageName,
                parentModule,
                parentModuleRootDirectory!!
            )
            ModuleType.KMM_DEFAULT,
            ModuleType.KMM_GATEWAY,
            ModuleType.KMM_DOMAIN,
            ModuleType.KMM_PRESENTATION -> createKmmModuleFiles(
                newModuleName,
                moduleType,
                packageName,
                parentModuleRootDirectory!!
            )
            ModuleType.KMM_FEATURE -> TODO()
        }

        val fullModuleName =
            if (parentModule.nameWithoutPrefix.isEmpty()) String() else ":${parentModule.nameWithoutPrefix}"
        editSettingsGradleFile(newModuleName, fullModuleName)
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

    private fun createSimpleModuleFiles(
        moduleName: String,
        moduleType: ModuleType,
        packageName: String,
        parentDirectory: Directory
    ) {
        val moduleDirectory = parentDirectory.createSubdirectory(moduleName)
        val srcDir = moduleDirectory.createSubdirectory("src")
        val mainDir = srcDir.createSubdirectory("main")
        val kotlinDir = mainDir.createSubdirectory("kotlin")
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
        val srcDir = moduleDirectory.createSubdirectory("src")
        val androidMain = srcDir.createSubdirectory("androidMain")
        val commonMain = srcDir.createSubdirectory("commonMain")
        val kotlinDir = commonMain.createSubdirectory("kotlin")
        val packageDirs = packageName.split('.').toMutableList()
        var topPackageDir = kotlinDir
        packageDirs.forEach {
            topPackageDir = topPackageDir.createSubdirectory(it)
        }
        featureSettingsRepository.loadModuleFiles(moduleType).forEach {
            when (it.fileType) {
                FileType.MANIFEST -> {
                    it.content = it.content.replace(Variable.PACKAGE_NAME.value, packageName)
                    androidMain.addFile(it)
                }
                FileType.GRADLE -> moduleDirectory.addFile(it)
            //    FileType.GIT_IGNORE -> moduleDirectory.addFile(it)
             //   FileType.PROGUARD -> moduleDirectory.addFile(it)
                FileType.KOTLIN -> topPackageDir.addFile(it)
                else -> {
                }
            }
        }
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
                    "\ninclude '${moduleName.replace('.', ':')}:$featureName'"
                )
            }
        }
    }
}
