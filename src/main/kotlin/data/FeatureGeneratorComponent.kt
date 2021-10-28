package data

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.XmlSerializerUtil
import model.FeatureSettings
import java.io.Serializable

@State(name = "FeatureGeneratorConfiguration",
    storages = [Storage(value = "featureGeneratorConfiguration.xml")])
class FeatureGeneratorComponent : Serializable, PersistentStateComponent<FeatureGeneratorComponent> {

    companion object {
        fun getInstance(project: Project) = ServiceManager.getService(project, FeatureGeneratorComponent::class.java)
    }

    var settings: FeatureSettings = FeatureSettings()

    override fun getState(): FeatureGeneratorComponent = this

    override fun loadState(state: FeatureGeneratorComponent) {
        XmlSerializerUtil.copyBean(state, this)
    }
}