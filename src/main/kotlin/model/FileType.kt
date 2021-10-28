package model


enum class FileType(private val displayName: String, val extension: String) {
    MANIFEST("AndroidManifest.xml", "xml"),
    GRADLE("build.gradle", "gradle"),
    GIT_IGNORE(".gitignore", "gitignore"),
    PROGUARD("proguard-rules.pro", "pro"),
    KOTLIN("KotlinFile", "kt"),
    LAYOUT_XML("Layout XML", "xml");

    override fun toString() = displayName
}