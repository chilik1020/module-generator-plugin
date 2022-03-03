package model

enum class ModuleType(val id: Int, val title: String, val description: String) {
    DEFAULT(0, "default", "default"),
    DOMAIN(1, "domain", "domain"),
    PRESENTATION(2, "presentation", "presentation"),
    FEATURE(3, "feature", "feature(domain and presentation)"),
    KMM_DEFAULT(4, "kmm default", "kmm default"),
    KMM_GATEWAY(5, "kmm-gateway", "kmm-gateway"),
    KMM_DOMAIN(6, "kmm-domain", "kmm-domain"),
    KMM_PRESENTATION(7, "kmm-presentation", "kmm-presentation"),
    KMM_FEATURE(8, "kmm-feature", "kmm-feature(kmm-gateway, kmm-domain and kmm-presentation)");

    override fun toString(): String = description
}