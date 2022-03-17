package ui.feature.settings.widget

import com.intellij.openapi.project.Project
import com.intellij.ui.JBSplitter
import model.FileType
import org.jetbrains.kotlin.idea.KotlinLanguage
import ui.feature.settings.FeatureSettingsState
import java.awt.BorderLayout
import javax.swing.BoxLayout
import javax.swing.JPanel

class FeatureSettingsPanel(project: Project) : JPanel() {

    val packagesPanel = PackagesPanel()
    val moduleTypesPanel = ModuleTypesPanel()
    val moduleFilesPanel = ModuleFilesPanel()
    private val codePanel = CodePanel(project, KotlinLanguage.INSTANCE, FileType.KOTLIN)

    var onTemplateTextChanged: ((String) -> Unit)? = null
        set(value) {
            field = value
            codePanel.onTemplateTextChanged = value
        }

    init {
        layout = BorderLayout()

        val contentPanel = JPanel().apply {
            layout = BorderLayout()

            val topPanel = JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                add(packagesPanel)
                add(JBSplitter(0.3f).apply {
                    firstComponent = moduleTypesPanel
                    secondComponent = moduleFilesPanel
                })
            }

            add(topPanel, BorderLayout.PAGE_START)

            add(JPanel().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)
                add(codePanel)
            }, BorderLayout.CENTER)
        }

        add(contentPanel, BorderLayout.CENTER)
    }

    fun render(state: FeatureSettingsState) {
        packagesPanel.render(state)
        moduleTypesPanel.render(state)
        moduleFilesPanel.render(state)
        codePanel.render(state)
    }
}