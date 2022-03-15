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

    private val simpleModuleTab = JPanel()
    val moduleNameTextField = JTextField()
    private val kmmGatewayNameTextField = JTextField()
    private val kmmDomainNameTextField = JTextField()
    private val kmmPresentationNameTextField = JTextField()
    private val moduleNameLabelTextField = JLabel("Module name:")
    private val kmmGatewayNameLabelTextField = JLabel("Gateway module name:")
    private val kmmDomainNameLabelTextField = JLabel("Domain module name:")
    private val kmmPresentationNameLabelTextField = JLabel("Presentation module name:")
    val packageTextField = JTextField()
    val moduleTypesComboBox = ComboBox<ModuleType>()
    val projectModulesComboBox = ComboBox<Module>()

    lateinit var currentState: NewFeatureState
    var onModuleTypeIndexChanged: ((Int) -> Unit)? = null
    var onModuleNameChanged: ((String) -> Unit)? = null
    var onPackageNameChanged: ((String) -> Unit)? = null
    var onParentModuleIndexChanged: ((Int) -> Unit)? = null
    var onKmmGatewayNameChanged: ((String) -> Unit)? = null
    var onKmmDomainNameChanged: ((String) -> Unit)? = null
    var onKmmPresentationNameChanged: ((String) -> Unit)? = null

    private var listenersBlocked = false

    init {
        initSimpleModulePanel()
        layout = BoxLayout(this, BoxLayout.Y_AXIS)

//        val tabbedPane = JTabbedPane()
//        tabbedPane.preferredSize = Dimension(950, 290)
//        //  tabbedPane.border = BevelBorder(BevelBorder.LOWERED, null, null, null, null)
//        tabbedPane.addTab("Simple", simpleModuleTab)
//
//        val kmm = JTextField("kmm")
//        tabbedPane.add("KMM", kmm)
//
//        val one = JTextField("one")
//        tabbedPane.add("one", one)
//
//        val two = JTextField("two")
//        tabbedPane.add("two", two)
//
//        tabbedPane.isEnabled = true
//        tabbedPane.tabPlacement = JTabbedPane.TOP;

        add(simpleModuleTab)
    }

    override fun getPreferredSize(): Dimension = Dimension(1000, 300)

    fun render(state: NewFeatureState) = state.run {

        listenersBlocked = true
        packageTextField.updateText(packageName)
        moduleNameTextField.updateText(moduleName)
        kmmGatewayNameTextField.updateText(kmmGatewaySubModuleName)
        kmmDomainNameTextField.updateText(kmmDomainSubModuleName)
        kmmPresentationNameTextField.updateText(kmmPresentationSubModuleName)
        when (this.selectedModuleType) {
            ModuleType.ANDROID_MODULE -> {
                moduleNameLabelTextField.text = "Module name:"
                setKmmSubModulesFieldsVisibility(false)

            }
            ModuleType.KMM_MODULE -> {
                moduleNameLabelTextField.text = "Module name:"
                setKmmSubModulesFieldsVisibility(false)
            }
            ModuleType.FEATURE -> {
                moduleNameLabelTextField.text = "Feature name:"
                setKmmSubModulesFieldsVisibility(false)
            }

            ModuleType.KMM_FEATURE -> {
                moduleNameLabelTextField.text = "Feature name:"
                setKmmSubModulesFieldsVisibility(true)
            }
            null -> {
                moduleNameLabelTextField.text = "Module name:"
                setKmmSubModulesFieldsVisibility(false)
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

        currentState = this
        listenersBlocked = false
    }

    private fun initSimpleModulePanel() {
        with(simpleModuleTab) {
            layout = GridBagLayout()
            add(JLabel("Module type:"), constraintsLeft(0, 0))
            add(moduleTypesComboBox, constraintsRight(1, 0))

            add(JLabel("Parent module:"), constraintsLeft(0, 1))
            add(projectModulesComboBox, constraintsRight(1, 1))

            add(moduleNameLabelTextField, constraintsLeft(0, 2))
            add(moduleNameTextField, constraintsRight(1, 2))

            add(JLabel("Package:"), constraintsLeft(0, 3))
            add(packageTextField, constraintsRight(1, 3))

            add(kmmGatewayNameLabelTextField, constraintsLeft(0, 4))
            add(kmmGatewayNameTextField, constraintsRight(1, 4))

            add(kmmDomainNameLabelTextField, constraintsLeft(0, 5))
            add(kmmDomainNameTextField, constraintsRight(1, 5))

            add(kmmPresentationNameLabelTextField, constraintsLeft(0, 6))
            add(kmmPresentationNameTextField, constraintsRight(1, 6))

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

            kmmDomainNameTextField.addTextChangeListener {
                if (!listenersBlocked) onKmmDomainNameChanged?.invoke(it)
            }

            kmmGatewayNameTextField.addTextChangeListener {
                if (!listenersBlocked) onKmmGatewayNameChanged?.invoke(it)
            }

            kmmPresentationNameTextField.addTextChangeListener {
                if (!listenersBlocked) onKmmPresentationNameChanged?.invoke(it)
            }
        }
    }

    private fun setKmmSubModulesFieldsVisibility(isVisible: Boolean) {
        kmmGatewayNameTextField.isVisible = isVisible
        kmmDomainNameTextField.isVisible = isVisible
        kmmPresentationNameTextField.isVisible = isVisible
        kmmGatewayNameLabelTextField.isVisible = isVisible
        kmmDomainNameLabelTextField.isVisible = isVisible
        kmmPresentationNameLabelTextField.isVisible = isVisible
    }
}