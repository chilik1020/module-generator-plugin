package ui.feature.settings.reducer

import data.file.File
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import model.ModuleFiles
import ui.feature.base.BaseReducer
import ui.feature.settings.FeatureSettingsEffect
import ui.feature.settings.FeatureSettingsState
import javax.inject.Inject

interface UpdateFileReducer {

    operator fun invoke(updatedFile: File)
}

class UpdateFileReducerImpl @Inject constructor(
    private val state: MutableStateFlow<FeatureSettingsState>,
    effect: MutableSharedFlow<FeatureSettingsEffect>,
    scope: CoroutineScope
) : BaseReducer<FeatureSettingsState, FeatureSettingsEffect>(state, effect, scope), UpdateFileReducer {

    override fun invoke(updatedFile: File) {
        pushState {
            val moduleFiles = modules[selectedModuleIndex!!]
            val newFiles = moduleFiles.files.toMutableList().apply {
                set(selectedFileIndex!!, updatedFile)
            }
            val newModules = modules.toMutableList().apply {
                set(selectedModuleIndex, ModuleFiles(moduleFiles.moduleType, newFiles))
            }
            copy(
                modules = newModules,
                isModified = true
            )
        }
    }
}