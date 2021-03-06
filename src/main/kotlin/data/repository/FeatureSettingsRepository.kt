package data.repository

import com.intellij.openapi.project.Project
import data.FeatureGeneratorComponent
import data.file.File
import model.FeatureSettings
import model.ModuleFiles
import model.ModuleType
import javax.inject.Inject

interface FeatureSettingsRepository {
    fun loadModuleTypesWithFiles(): List<ModuleFiles>
    fun update(setting: FeatureSettings)
    fun loadModuleTypes(): List<ModuleType>
    fun loadModuleFiles(moduleType: ModuleType): List<File>
    fun loadDefaultPackage(): String
    fun loadDefaultKmmPackage(): String
    fun loadDefaultKmmFeatureSubmodulePrefix(): String
}

class FeatureSettingsRepositoryImpl @Inject constructor(private val project: Project) : FeatureSettingsRepository {

    override fun loadModuleTypesWithFiles(): List<ModuleFiles> {
        val settings = loadSettings()
        return settings.modules.filter { it.showInSettings }.map { moduleType ->
            ModuleFiles(
                moduleType,
                settings.files.filter { it.moduleType == moduleType }
            )
        }
    }

    override fun update(setting: FeatureSettings) = FeatureGeneratorComponent.getInstance(project).run {
        this.settings = setting
    }

    override fun loadModuleTypes(): List<ModuleType> = loadSettings().modules

    override fun loadModuleFiles(moduleType: ModuleType) = loadSettings().files.filter { it.moduleType == moduleType }

    override fun loadDefaultPackage(): String = loadSettings().defaultPackage

    override fun loadDefaultKmmPackage(): String = loadSettings().defaultKmmPackage

    override fun loadDefaultKmmFeatureSubmodulePrefix(): String = loadSettings().defaultKmmFeatureSubmodulePrefix

    private fun loadSettings() = FeatureGeneratorComponent.getInstance(project).settings
}

