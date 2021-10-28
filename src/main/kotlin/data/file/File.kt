package data.file

import model.FileType
import model.ModuleType

data class File(
    val moduleType: ModuleType = ModuleType.DEFAULT,
    val name: String,
    var content: String,
    val fileType: FileType
) {
    override fun toString(): String = "$name.${fileType.extension}"
}