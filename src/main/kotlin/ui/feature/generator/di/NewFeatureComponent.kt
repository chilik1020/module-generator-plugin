package ui.feature.generator.di

import com.intellij.openapi.project.Project
import dagger.BindsInstance
import dagger.Component
import data.file.CurrentPath
import ui.feature.generator.NewFeatureDialog
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            NewFeatureModule::class
        ]
)
interface NewFeatureComponent {

    @Component.Factory
    interface Builder {
        fun create(@BindsInstance project: Project, @BindsInstance currentPath: CurrentPath?): NewFeatureComponent
    }

    fun inject(dialog: NewFeatureDialog)
}