package model

enum class ModuleType(
    val id: Int,
    val showInDialog: Boolean,
    val showInSettings: Boolean,
    val title: String,
    val description: String
) {
    ANDROID_MODULE(0, true, true, "Android module", "Android Module"),
    DOMAIN(1, false, true, "domain", "domain"),
    PRESENTATION(2, false, true, "presentation", "presentation"),
    FEATURE(3, true, false, "Feature", "Feature(domain and presentation)"),
    KMM_MODULE(4, true, true, "KMM Module", "KMM Module"),
    KMM_GATEWAY(5, false, true, "kmm-pi-features-%s-gateway", "KMM Gateway"),
    KMM_DOMAIN(6, false, true, "kmm-pi-features-%s-domain", "KMM Domain"),
    KMM_PRESENTATION(7, false, true, "kmm-pi-features-%s-presentation", "KMM Presentation"),
    KMM_FEATURE(8, true, false, "KMM Feature", "KMM Feature(kmm-gateway, kmm-domain and kmm-presentation)");

    override fun toString(): String = description
}