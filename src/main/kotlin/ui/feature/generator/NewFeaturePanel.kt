package ui.feature.generator

import com.intellij.openapi.ui.ComboBox
import model.Module
import model.ModuleType
import ui.feature.settings.widget.constraintsLeft
import ui.feature.settings.widget.constraintsRight
import util.addTextChangeListener
import util.updateText
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.BoxLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class NewFeaturePanel : JPanel() {

    val moduleNameTextField = JTextField()
    private val moduleNameLabelTextField = JLabel("Module name:")
    val packageTextField = JTextField()
    val moduleTypesComboBox = ComboBox<ModuleType>()
    val projectModulesComboBox = ComboBox<Module>()

    var onModuleTypeIndexChanged: ((Int) -> Unit)? = null
    var onModuleNameChanged: ((String) -> Unit)? = null
    var onPackageNameChanged: ((String) -> Unit)? = null
    var onParentModuleIndexChanged: ((Int) -> Unit)? = null

    private var listenersBlocked = false

    init {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        add(JPanel().apply {
            layout = GridBagLayout()
            add(JLabel("Module type:"), constraintsLeft(0, 0))
            add(moduleTypesComboBox, constraintsRight(1, 0))

            add(moduleNameLabelTextField, constraintsLeft(0, 1))
            add(moduleNameTextField, constraintsRight(1, 1))

            add(JLabel("Package:"), constraintsLeft(0, 2))
            add(packageTextField, constraintsRight(1, 2))

            add(JLabel("Parent module:"), constraintsLeft(0, 3))
            add(projectModulesComboBox, constraintsRight(1, 3))

            moduleTypesComboBox.addActionListener {
                if (!listenersBlocked) onModuleTypeIndexChanged?.invoke(moduleTypesComboBox.selectedIndex)
            }

            moduleNameTextField.addTextChangeListener {
                if (!listenersBlocked) onModuleNameChanged?.invoke(it)
            }

            packageTextField.addTextChangeListener {
                if (!listenersBlocked) onPackageNameChanged?.invoke(it)
            }

            projectModulesComboBox.addActionListener {
                if (!listenersBlocked) onParentModuleIndexChanged?.invoke(projectModulesComboBox.selectedIndex)
            }
        })
    }

    override fun getPreferredSize(): Dimension = Dimension(550, 200)

    fun render(state: NewFeatureState) = state.run {
        listenersBlocked = true
        packageTextField.updateText(packageName)
        moduleNameTextField.updateText(moduleName)
        when (this.selectedModuleType) {
            ModuleType.DEFAULT -> {
                moduleNameLabelTextField.text = "Module name:"
                moduleNameTextField.isEnabled = true
            }
            ModuleType.DOMAIN -> {
                moduleNameLabelTextField.text = "Module name:"
                moduleNameTextField.isEnabled = false
            }
            ModuleType.PRESENTATION -> {
                moduleNameLabelTextField.text = "Module name:"
                moduleNameTextField.isEnabled = false
            }
            ModuleType.FEATURE -> {
                moduleNameLabelTextField.text = "Feature name:"
                moduleNameTextField.isEnabled = true
            }
            null -> {
                moduleNameLabelTextField.text = "Module name:"
                moduleNameTextField.isEnabled = true
            }
        }

        if (moduleTypesComboBox.itemCount == 0) {
            moduleTypesComboBox.removeAllItems()
            this.modulesTypes.forEach { moduleTypesComboBox.addItem(it) }
        }

        moduleTypesComboBox.selectedItem = this.selectedModuleType

        if (projectModulesComboBox.itemCount == 0) {
            projectModulesComboBox.removeAllItems()
            this.projectModules.forEach { projectModulesComboBox.addItem(it) }
        }
        projectModulesComboBox.selectedItem = this.selectedProjectModule

        listenersBlocked = false
    }
}