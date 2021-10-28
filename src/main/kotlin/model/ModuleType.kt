package model

enum class ModuleType(val id: Int, val title: String, val description: String) {
    DEFAULT(0, "simple", "simple"),
    DOMAIN(1, "domain", "domain"),
    PRESENTATION(2, "presentation", "presentation"),
    FEATURE(3, "feature", "feature(domain and presentation)");

    override fun toString(): String = description
}