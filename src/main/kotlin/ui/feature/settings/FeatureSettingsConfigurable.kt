package ui.feature.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.project.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ui.feature.settings.di.DaggerFeatureSettingsComponent
import ui.feature.settings.widget.FeatureSettingsPanel
import javax.inject.Inject
import javax.swing.JComponent

class FeatureSettingsConfigurable(private val project: Project) : Configurable {

    private lateinit var panel: FeatureSettingsPanel

    @Inject
    lateinit var state: MutableStateFlow<FeatureSettingsState>

    @Inject
    lateinit var effect: MutableSharedFlow<FeatureSettingsEffect>

    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var viewModel: FeatureSettingsViewModel


    override fun createComponent(): JComponent? {
        DaggerFeatureSettingsComponent.factory().create(project).inject(this)
        panel = FeatureSettingsPanel(project)

        panel.moduleTypesPanel.onItemSelected = { viewModel.reduce(FeatureSettingsAction.SelectModule(it)) }
        panel.moduleFilesPanel.onItemSelected = { viewModel.reduce(FeatureSettingsAction.SelectFile(it)) }
        panel.onTemplateTextChanged = { viewModel.reduce(FeatureSettingsAction.ChangeTemplate(it)) }
        panel.packagesPanel.onPackageChanged = { viewModel.reduce(FeatureSettingsAction.ChangePackage(it)) }
        panel.packagesPanel.onKmmPackageChanged = { viewModel.reduce(FeatureSettingsAction.ChangeKmmPackage(it)) }
        panel.packagesPanel.onSubModulePrefixChanged = { viewModel.reduce(FeatureSettingsAction.ChangeFeatureSubmodulePrefix(it)) }

        scope.launch { state.collect { panel.render(it) } }
        scope.launch { effect.collect {} }
        return panel
    }

    override fun isModified(): Boolean = if (::state.isInitialized) state.value.isModified else false

    override fun apply() = viewModel.reduce(FeatureSettingsAction.ApplySettings)

    override fun reset() = viewModel.reduce(FeatureSettingsAction.ResetSettings)

    override fun getDisplayName() = SETTINGS_DISPLAY_NAME

    override fun disposeUIResources() {
        scope.cancel()
        super.disposeUIResources()
    }
    
    companion object {
        private const val SETTINGS_DISPLAY_NAME = "Prior module generator plugin"
    }
}