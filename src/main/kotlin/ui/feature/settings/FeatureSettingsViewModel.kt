package ui.feature.settings

import ui.feature.settings.reducer.*
import javax.inject.Inject

class FeatureSettingsViewModel @Inject constructor(
    private val applySettingsReducer: ApplyFeatureSettingsReducer,
    private val resetSettingsReducer: ResetFeatureSettingsReducer,
    private val selectModuleReducer: SelectModuleReducer,
    private val selectFileReducer: SelectFileReducer,
    private val changeTemplateReducer: ChangeTemplateReducer,
    private val changePackageReducer: ChangePackageReducer,
    private val changeKmmPackageReducer: ChangeKmmPackageReducer,
    private val changeKmmFeatureSubmodulePrefixReducer: ChangeKmmFeatureSubmodulePrefixReducer
) {

    init {
        resetSettingsReducer()
    }

    fun reduce(action: FeatureSettingsAction) = when (action) {
        is FeatureSettingsAction.ApplySettings -> applySettingsReducer()
        is FeatureSettingsAction.ResetSettings -> resetSettingsReducer()
        is FeatureSettingsAction.SelectModule -> selectModuleReducer(action.index)
        is FeatureSettingsAction.SelectFile -> selectFileReducer(action.index)
        is FeatureSettingsAction.ChangeTemplate -> changeTemplateReducer(action.text)
        is FeatureSettingsAction.ChangePackage -> changePackageReducer(action.text)
        is FeatureSettingsAction.ChangeKmmPackage -> changeKmmPackageReducer(action.text)
        is FeatureSettingsAction.ChangeFeatureSubmodulePrefix -> changeKmmFeatureSubmodulePrefixReducer(action.text)
    }
}