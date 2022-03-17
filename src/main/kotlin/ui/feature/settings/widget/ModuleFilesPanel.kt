package ui.feature.settings.widget

import com.intellij.ui.CollectionListModel
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.ToolbarDecorator
import com.intellij.ui.components.JBList
import data.file.File
import ui.feature.settings.FeatureSettingsState
import java.awt.GridLayout
import javax.swing.JPanel
import javax.swing.ListSelectionModel

class ModuleFilesPanel : JPanel() {

    private val listModel = CollectionListModel<File>()
    private val list = JBList(listModel).apply {
        selectionMode = ListSelectionModel.SINGLE_SELECTION
    }
    private val toolbarDecorator: ToolbarDecorator = ToolbarDecorator.createDecorator(list)

    var onItemSelected: ((Int) -> Unit)? = null

    private var listenersBlocked = false

    init {
        border = IdeBorderFactory.createTitledBorder(LABEL_MODULE_FILES, false)
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
        state.selectedModule?.files?.forEachIndexed { index, file ->
            if (index < listModel.size && listModel.getElementAt(index) != file) {
                listModel.setElementAt(file, index)
            } else if (index >= listModel.size) {
                listModel.add(file)
            }
        }
        if (listModel.size > state.selectedModule?.files?.size ?: 0) {
            listModel.removeRange(state.selectedModule?.files?.size ?: 0, listModel.size - 1)
        }
        listenersBlocked = false
    }
    
    companion object {
        private const val LABEL_MODULE_FILES = "Module files"
    }
}