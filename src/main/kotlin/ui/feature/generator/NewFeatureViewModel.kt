package ui.feature.generator

import ui.feature.generator.reducer.*
import javax.inject.Inject

class NewFeatureViewModel @Inject constructor(
    initReducer: InitReducer,
    private val okClickReducer: OkClickReducer,
    private val onModuleTypeIndexChangedReducer: ModuleTypeIndexChangedReducer,
    private val onModuleNameChangedReducer: ModuleNameChangedReducer,
    private val onParentModuleIndexChangedReducer: ParentModuleIndexChangedReducer,
    private val onPackageNameChangedReducer: PackageNameChangedReducer,
    private val onKmmGatewayNameChangedReducer: KmmGatewayNameChangedReducer,
    private val onKmmDomainNameChangedReducer: KmmDomainNameChangedReducer,
    private val onKmmPresentationNameChangedReducer: KmmPresentationNameChangedReducer
) {

    init {
        initReducer()
    }

    fun reduce(action: NewFeatureAction) = when (action) {
        is NewFeatureAction.OkClicked -> okClickReducer(
            action.state,
            action.newModuleName,
            action.packageName,
            action.moduleType,
            action.parentModule
        )
        is NewFeatureAction.ModuleTypeIndexChanged -> onModuleTypeIndexChangedReducer(action.index)
        is NewFeatureAction.ModuleNameChanged -> onModuleNameChangedReducer(action.text)
        is NewFeatureAction.ParentModuleChanged -> onParentModuleIndexChangedReducer(action.index)
        is NewFeatureAction.PackageNameChanged -> onPackageNameChangedReducer(action.text)
        is NewFeatureAction.KmmGatewayNameChanged -> onKmmGatewayNameChangedReducer(action.text)
        is NewFeatureAction.KmmDomainNameChanged -> onKmmDomainNameChangedReducer(action.text)
        is NewFeatureAction.KmmPresentationNameChanged -> onKmmPresentationNameChangedReducer(action.text)
    }
}