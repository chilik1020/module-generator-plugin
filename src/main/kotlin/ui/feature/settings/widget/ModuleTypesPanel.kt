package ui.feature.settings.widget

import com.intellij.ui.CollectionListModel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import model.ModuleType
import ui.feature.settings.FeatureSettingsState
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel

class ModuleTypesPanel : JPanel() {

    private val listModel = CollectionListModel<ModuleType>()
    val list = JBList(listModel).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
    }
    private val toolbarDecorator: ToolbarDecorator = ToolbarDecorator.createDecorator(list)

    var onItemSelected: ((Int) -> Unit)? = null

    private var listenersBlocked = false

    init {
        border = IdeBorderFactory.createTitledBorder("Modules", false)
        layout = GridLayout(1, 1)

        toolbarDecorator.apply {
            add(createPanel())
        }

        list.addListSelectionListener {
            if (!it.valueIsAdjusting && !listenersBlocked) onItemSelected?.invoke(list.selectedIndex)
        }
    }

    fun render(state: FeatureSettingsState) {
        listenersBlocked = true
        state.modules.forEachIndexed { index, moduleFiles ->
            if (index < listModel.size && listModel.getElementAt(index) != moduleFiles.moduleType) {
                listModel.setElementAt(moduleFiles.moduleType, index)
            } else if (index >= listModel.size) {
                listModel.add(moduleFiles.moduleType)
            }
        }
        if (listModel.size > state.modules.size) {
            listModel.removeRange(state.modules.size, listModel.size - 1)
        }
        if (state.selectedModuleIndex != null && list.selectedIndex != state.selectedModuleIndex) {
            list.selectedIndex = state.selectedModuleIndex
        }
        listenersBlocked = false
    }
}