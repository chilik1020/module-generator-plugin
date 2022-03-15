package model

enum class ModuleType(val id: Int, val showInDialog: Boolean, val title: String, val description: String) {
    ANDROID_MODULE(0, true, "Android module", "Android Module"),
    DOMAIN(1, false, "domain", "domain"),
    PRESENTATION(2, false, "presentation", "presentation"),
    FEATURE(3, true, "Feature", "Feature(domain and presentation)"),
    KMM_MODULE(4, true, "KMM Module", "KMM Module"),
    KMM_GATEWAY(5, false, "kmm-pi-features-%s-gateway", "KMM Gateway"),
    KMM_DOMAIN(6, false, "kmm-pi-features-%s-domain", "KMM Domain"),
    KMM_PRESENTATION(7, false, "kmm-pi-features-%s-presentation", "KMM Presentation"),
    KMM_FEATURE(8, true, "KMM Feature", "KMM Feature(kmm-gateway, kmm-domain and kmm-presentation)");

    override fun toString(): String = description
}