package ui.feature.settings

import data.file.File
import model.ModuleFiles

data class FeatureSettingsState(
    val isModified: Boolean = false,
    val templateCode: String = "",
    val sampleCode: String = "",
    val modules: List<ModuleFiles> = emptyList(),
    val selectedModuleIndex: Int? = null,
    val selectedFileIndex: Int? = null
) {

    val selectedModule: ModuleFiles?
        get() = selectedModuleIndex?.let { modules[it] }

    val selectedFile: File?
        get() = selectedFileIndex?.let { selectedModule?.files?.get(it) }
}