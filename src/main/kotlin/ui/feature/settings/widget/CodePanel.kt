package ui.feature.settings.widget

import com.intellij.lang.Language
import com.intellij.openapi.project.Project
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.LanguageTextField
import com.intellij.ui.components.JBScrollPane
import model.FileType
import ui.feature.settings.FeatureSettingsState
import util.addTextChangeListener
import util.updateText
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.BoxLayout
import javax.swing.JPanel

class CodePanel(
    private val project: Project,
    language: Language,
    private val fileType: FileType
) : JPanel() {

    var onTemplateTextChanged: ((String) -> Unit)? = null

    private val templateTextField = createLanguageTextField(language)

    private var listenersBlocked = false

    init {
        layout = GridLayout(2, 1)
        val templatePanel = createTemplatePanel()
        add(templatePanel)
        templateTextField.addTextChangeListener { if (!listenersBlocked) onTemplateTextChanged?.invoke(it) }
    }

    private fun createTemplatePanel() =
        JPanel().apply {
            border = IdeBorderFactory.createTitledBorder(LABEL_CODE_TEMPLATE, true)
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            add(JBScrollPane(templateTextField))
        }

    private fun createLanguageTextField(language: Language, isEnabled: Boolean = true) =
        LanguageTextField(language, project, String(), false).apply {
            this.isEnabled = isEnabled
        }

    fun render(state: FeatureSettingsState) {
        listenersBlocked = true
        val selectedFile = state.selectedFile
        if (selectedFile != null) {
            setEnabledAll(true)
            templateTextField.updateText(selectedFile.content)
        } else {
            templateTextField.updateText(String())
            setEnabledAll(false)
        }
        listenersBlocked = false
    }

    private fun setEnabledAll(isEnabled: Boolean) {
        templateTextField.isEnabled = isEnabled
    }

    override fun getPreferredSize(): Dimension? {
        val original = super.getPreferredSize()
        return Dimension(original.width, 400)
    }

    companion object {
        private const val LABEL_CODE_TEMPLATE = "Code Template"
    }
}