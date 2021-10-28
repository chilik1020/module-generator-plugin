package ui.feature.settings.di

import com.intellij.openapi.project.Project
import dagger.BindsInstance
import dagger.Component
import ui.feature.settings.FeatureSettingsConfigurable
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FeatureSettingsModule::class
    ]
)
interface FeatureSettingsComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance project: Project): FeatureSettingsComponent
    }

    fun inject(configurable: FeatureSettingsConfigurable)
}