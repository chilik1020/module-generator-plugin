package data.repository

import com.intellij.psi.PsiFile
import data.file.ProjectStructure
import data.file.SourceRoot
import model.*
import javax.inject.Inject

interface SourceRootRepository {

    fun findCodeSourceRoot(module: Module, sourceSet: String = "main"): SourceRoot?
    fun findResourcesSourceRoot(module: Module): SourceRoot?
    fun findModuleSourceRoot(module: Module): SourceRoot?
    fun getProjectRoot(): SourceRoot?
    fun getSettingsGradleFile(): PsiFile?
}

class SourceRootRepositoryImpl @Inject constructor(
    private val projectStructure: ProjectStructure
) : SourceRootRepository {

    override fun findCodeSourceRoot(module: Module, sourceSet: String) =
        projectStructure.findSourceRoots(module).firstOrNull {
            val pathTrimmed = it.path.removeModulePathPrefix(module)
            pathTrimmed.contains(FOLDER_SRC_NAME, true)
                && pathTrimmed.contains(sourceSet)
                && !pathTrimmed.contains(FOLDER_ASSETS_NAME, true)
                && !pathTrimmed.contains(FOLDER_RES_NAME, true)
        }

    override fun findResourcesSourceRoot(module: Module) =
        projectStructure.findSourceRoots(module).firstOrNull {
            val pathTrimmed = it.path.removeModulePathPrefix(module)
            pathTrimmed.contains(FOLDER_SRC_NAME, true)
                && pathTrimmed.contains(FOLDER_MAIN_NAME, true)
                && pathTrimmed.contains(FOLDER_RES_NAME, true)
        }

    override fun findModuleSourceRoot(module: Module): SourceRoot? =
        projectStructure.findModuleRoot(module)

    override fun getProjectRoot(): SourceRoot? =
        projectStructure.getProjectSourceRoot()

    override fun getSettingsGradleFile(): PsiFile? =
        projectStructure.findSettingsGradleFile()


    private fun String.removeModulePathPrefix(module: Module) =
        removePrefix(projectStructure.getProjectPath() + "/" + module.nameWithoutPrefix.replace(".", "/"))
}