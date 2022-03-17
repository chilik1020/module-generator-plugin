package data.repository

import data.file.ProjectStructure
import model.Module
import javax.inject.Inject

interface ModuleRepository {

    fun getAllModules(): List<Module>
}

class ModuleRepositoryImpl @Inject constructor(
    private val projectStructure: ProjectStructure
) : ModuleRepository {

    override fun getAllModules() =
        projectStructure.getAllModules()
            .map {
                Module(
                    name = it,
                    nameWithoutPrefix = if (it.contains("${projectStructure.getProjectName()}.")) {
                        it.replace("${projectStructure.getProjectName()}.", String())
                    } else {
                        it.replace(projectStructure.getProjectName(), String())
                    }
                )
            }
}