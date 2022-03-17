package data.file

import com.intellij.openapi.module.ModuleManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.project.guessModuleDir
import com.intellij.openapi.project.guessProjectDir
import com.intellij.psi.PsiFile
import model.Module
import org.jetbrains.kotlin.idea.core.util.toPsiFile
import org.jetbrains.kotlin.idea.util.sourceRoots
import java.io.FileInputStream
import javax.inject.Inject

interface ProjectStructure {

    fun findSourceRoots(module: Module): List<SourceRoot>
    fun findModuleRoot(module: Module): SourceRoot?
    fun findSettingsGradleFile(): PsiFile?
    fun getAllModules(): List<String>
    fun getProjectName(): String
    fun getProjectPath(): String
    fun getProjectSourceRoot(): SourceRoot?
}

class ProjectStructureImpl @Inject constructor(private val project: Project) : ProjectStructure {

    override fun findSourceRoots(module: Module) =
        ModuleManager.getInstance(project).findModuleByName(module.name)?.sourceRoots?.map {
            SourceRootImpl(project, it)
        }
            ?: throw IllegalStateException("${module.name} $EXCEPTION_MODULE_NOT_EXITS")

    override fun findModuleRoot(module: Module) =
        ModuleManager.getInstance(project).findModuleByName(module.name)?.guessModuleDir()?.let {
            SourceRootImpl(project, it)
        }

    override fun findSettingsGradleFile(): PsiFile? =
        project.guessProjectDir()?.findChild(SETTINGS_GRADLE_FILE_NAME)?.toPsiFile(project)

    override fun getAllModules() = ModuleManager.getInstance(project).modules.map { it.name }

    override fun getProjectName() = project.name

    override fun getProjectPath(): String = project.basePath ?: ""

    override fun getProjectSourceRoot(): SourceRoot? =
        project.guessProjectDir()?.let {
            SourceRootImpl(project, it)
        }

    companion object {
        private const val SETTINGS_GRADLE_FILE_NAME = "settings.gradle"
        private const val EXCEPTION_MODULE_NOT_EXITS = "module doesn't exist!"
    }
}