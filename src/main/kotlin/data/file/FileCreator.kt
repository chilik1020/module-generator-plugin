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

    override fun createFeatureFiles(newModuleName: String, packageName: String, moduleType: ModuleType, parentModule: Module) {
        val parentModuleRootDirectory = findModuleRootDirectory(parentModule) ?: findProjectRootDirectory()

        if (moduleType == ModuleType.FEATURE) {
            val featureRootDirectory = parentModuleRootDirectory?.run { createSubdirectory(newModuleName) }
            createModuleFiles(
                ModuleType.DOMAIN.title,
                ModuleType.DOMAIN,
                "$packageName.${ModuleType.DOMAIN.title}",
                featureRootDirectory!!
            )

            createModuleFiles(
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
        } else {
            createModuleFiles(newModuleName, moduleType, packageName, parentModuleRootDirectory!!)
        }
        val fullModuleName = if (parentModule.nameWithoutPrefix.isEmpty()) String() else ":${parentModule.nameWithoutPrefix}"
        editSettingsGradleFile(newModuleName, fullModuleName)
    }

    private fun createModuleFiles(moduleName: String, moduleType: ModuleType, packageName: String, parentDirectory: Directory) {
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
