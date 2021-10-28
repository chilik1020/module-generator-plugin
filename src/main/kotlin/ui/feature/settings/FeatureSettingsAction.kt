package ui.feature.settings

sealed class FeatureSettingsAction {

    object ApplySettings : FeatureSettingsAction()
    object ResetSettings : FeatureSettingsAction()
    data class SelectModule(val index: Int) : FeatureSettingsAction()
    data class SelectFile(val index: Int) : FeatureSettingsAction()
    data class ChangeTemplate(val text: String) : FeatureSettingsAction()
}