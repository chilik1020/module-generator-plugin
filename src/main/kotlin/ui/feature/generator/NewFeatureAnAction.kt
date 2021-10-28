package ui.feature.generator

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.module.ModuleUtil
import data.file.CurrentPath
import model.Module
import org.jetbrains.kotlin.idea.core.util.writeString
import java.io.File
import java.io.RandomAccessFile

class NewFeatureAnAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val currentPath = event.getData(PlatformDataKeys.VIRTUAL_FILE)?.let {
            val moduleName = ModuleUtil.findModuleForFile(it, event.project!!)?.name ?: ""
            val module = Module(moduleName, moduleName.replace("${event.project!!.name}.", ""))
            CurrentPath(it.path, it.isDirectory, module)
        }
        NewFeatureDialog(event.project!!, currentPath).show()
    }
}