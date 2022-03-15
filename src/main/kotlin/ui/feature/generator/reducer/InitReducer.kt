package ui.feature.generator.reducer

import data.file.CurrentPath
import data.file.PackageExtractor
import data.repository.FeatureSettingsRepository
import data.repository.ModuleRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import ui.feature.base.BaseReducer
import ui.feature.generator.NewFeatureEffect
import ui.feature.generator.NewFeatureState
import javax.inject.Inject

interface InitReducer {

    operator fun invoke()
}

class FeatureInitReducer @Inject constructor(
    private val state: MutableStateFlow<NewFeatureState>,
    effect: MutableSharedFlow<NewFeatureEffect>,
    scope: CoroutineScope,
    private val packageExtractor: PackageExtractor,
    private val moduleRepository: ModuleRepository,
    private val currentPath: CurrentPath?,
    private val featureSettingsRepository: FeatureSettingsRepository
) : BaseReducer<NewFeatureState, NewFeatureEffect>(state, effect, scope), InitReducer {

    override fun invoke() = pushState {
        copy(
            moduleName = String(),
            packageName = packageExtractor.extractFromCurrentPath(),
            modulesTypes = featureSettingsRepository.loadModuleTypes().filter { it.showInDialog },
            projectModules = moduleRepository.getAllModules(),
            selectedProjectModule = currentPath?.module
        )
    }
}