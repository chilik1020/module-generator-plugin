package util

import com.intellij.ui.LanguageTextField
import javax.swing.JTextField
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

fun JTextField.addTextChangeListener(onChange: (String) -> Unit) =
    object : DocumentListener {
        override fun changedUpdate(e: DocumentEvent?) = onChange(text)
        override fun insertUpdate(e: DocumentEvent?) = onChange(text)
        override fun removeUpdate(e: DocumentEvent?) = onChange(text)
    }.apply { document.addDocumentListener(this) }

fun LanguageTextField.addTextChangeListener(onChange: (String) -> Unit) =
    object : com.intellij.openapi.editor.event.DocumentListener {
        override fun documentChanged(event: com.intellij.openapi.editor.event.DocumentEvent) = onChange(text)
    }.apply { document.addDocumentListener(this) }

fun JTextField.updateText(newText: String) {
    if (text != newText) {
        text = newText
    }
}

fun LanguageTextField.updateText(newText: String) {
    if (text != newText) {
        text = newText
    }
}