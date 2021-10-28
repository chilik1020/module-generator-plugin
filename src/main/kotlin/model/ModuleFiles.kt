package model

import data.file.File

data class ModuleFiles(
    val moduleType: ModuleType,
    val files: List<File>
)