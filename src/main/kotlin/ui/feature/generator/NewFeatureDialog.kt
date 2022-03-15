package ui.feature.generator

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import data.file.CurrentPath
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import model.Module
import model.ModuleType
import ui.feature.generator.di.DaggerNewFeatureComponent
import javax.inject.Inject

class NewFeatureDialog(project: Project, currentPath: CurrentPath?) : DialogWrapper(true) {

    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var state: MutableStateFlow<NewFeatureState>

    @Inject
    lateinit var effect: MutableSharedFlow<NewFeatureEffect>

    @Inject
    lateinit var viewModel: NewFeatureViewModel

    private val panel = NewFeaturePanel()

    init {
        DaggerNewFeatureComponent.factory().create(project, currentPath).inject(this)
        scope.launch { state.collect { it.render() } }
        scope.launch { effect.collect { handleEffect(it) } }
        panel.onModuleTypeIndexChanged = { viewModel.reduce(NewFeatureAction.ModuleTypeIndexChanged(it)) }
        panel.onModuleNameChanged = { viewModel.reduce(NewFeatureAction.ModuleNameChanged(it)) }
        panel.onParentModuleIndexChanged = { viewModel.reduce(NewFeatureAction.ParentModuleChanged(it)) }
        panel.onPackageNameChanged = { viewModel.reduce(NewFeatureAction.PackageNameChanged(it)) }
        panel.onKmmGatewayNameChanged = { viewModel.reduce(NewFeatureAction.KmmGatewayNameChanged(it)) }
        panel.onKmmDomainNameChanged = { viewModel.reduce(NewFeatureAction.KmmDomainNameChanged(it)) }
        panel.onKmmPresentationNameChanged = { viewModel.reduce(NewFeatureAction.KmmPresentationNameChanged(it)) }
        init()
    }

    override fun doOKAction() = viewModel.reduce(
        NewFeatureAction.OkClicked(
            state = panel.currentState,
            newModuleName = panel.moduleNameTextField.text,
            packageName = panel.packageTextField.text,
            moduleType = panel.moduleTypesComboBox.selectedItem as ModuleType,
            parentModule = panel.projectModulesComboBox.selectedItem as Module
        ))

    override fun createCenterPanel() = panel

    override fun dispose() {
        scope.cancel()
        super.dispose()
    }

    private fun NewFeatureState.render() = panel.render(this)

    private fun handleEffect(effect: NewFeatureEffect) = when (effect) {
        NewFeatureEffect.Close -> close(OK_EXIT_CODE)
    }
}