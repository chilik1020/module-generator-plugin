package ui.feature.settings.widget

import ui.feature.settings.FeatureSettingsState
import util.addTextChangeListener
import util.updateText
import java.awt.Dimension
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class PackagesPanel : JPanel() {

    private val packageTextField = JTextField()
    private val kmmPackageTextField = JTextField()
    private val kmmSubmodulePrefixTextField = JTextField()

    private var listenersBlocked = false

    var onPackageChanged: ((String) -> Unit)? = null
    var onKmmPackageChanged: ((String) -> Unit)? = null
    var onSubModulePrefixChanged: ((String) -> Unit)? = null

    init {
        layout = GridBagLayout()

        add(JLabel(LABEL_BASE_PACKAGE), constraintsLeft(0, 0))
        add(packageTextField, constraintsRight(1, 0))
        packageTextField.addTextChangeListener { if (!listenersBlocked) onPackageChanged?.invoke(it) }

        add(JLabel(LABEL_KMM_BASE_PACKAGE), constraintsLeft(0, 1))
        add(kmmPackageTextField, constraintsRight(1, 1))
        kmmPackageTextField.addTextChangeListener { if (!listenersBlocked) onKmmPackageChanged?.invoke(it) }

        add(JLabel(LABEL_KMM_SUBMODULES_PREFIX), constraintsLeft(0, 2))
        add(kmmSubmodulePrefixTextField, constraintsRight(1, 2))
        kmmSubmodulePrefixTextField.addTextChangeListener { if (!listenersBlocked) onSubModulePrefixChanged?.invoke(it) }
    }

    fun render(state: FeatureSettingsState) {
        listenersBlocked = true

        packageTextField.updateText(state.basePackage)
        kmmPackageTextField.updateText(state.kmmBasePackage)
        kmmSubmodulePrefixTextField.updateText(state.featureSubmodulesPrefix)

        listenersBlocked = false
    }

    override fun getPreferredSize(): Dimension? {
        val original = super.getPreferredSize()
        return Dimension(original.width, original.height)
    }
    
    companion object {
        private const val LABEL_BASE_PACKAGE = "Base package:"
        private const val LABEL_KMM_BASE_PACKAGE = "Kmm base package:"
        private const val LABEL_KMM_SUBMODULES_PREFIX = "Kmm feature's submodules prefix:"
    }
}