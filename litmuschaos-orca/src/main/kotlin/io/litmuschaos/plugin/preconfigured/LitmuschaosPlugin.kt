package io.litmuschaos.plugin.preconfigured

import com.netflix.spinnaker.kork.plugins.api.PluginSdks
import com.netflix.spinnaker.orca.api.preconfigured.jobs.PreconfiguredJobConfigurationProvider
import com.netflix.spinnaker.orca.clouddriver.config.KubernetesPreconfiguredJobProperties
import org.pf4j.Extension
import org.pf4j.Plugin
import org.pf4j.PluginWrapper


class LitmuschaosPlugin(wrapper: PluginWrapper): Plugin(wrapper) {

    override fun start() {
        System.out.println("LitmuschaosPlugin.start()")
    }

    override fun stop() {
        System.out.println("LitmuschaosPlugin.stop()")
    }
}

@Extension
class LitmuschaosPreconfiguredStage(val pluginSdks: PluginSdks, val configuration: PluginConfig) : PreconfiguredJobConfigurationProvider {
    override fun getJobConfigurations(): List<KubernetesPreconfiguredJobProperties> {
        val jobProperties = pluginSdks.yamlResourceLoader().loadResource("io/litmuschaos/plugin/preconfigured/litmuschaos.yaml", KubernetesPreconfiguredJobProperties::class.java)
        if (!configuration.account.isNullOrEmpty()) {
            jobProperties.account = configuration.account
        }
        if (!configuration.namespace.isNullOrEmpty()) {
            jobProperties.manifest.metadata.namespace = configuration.namespace
        }
        if (!configuration.application.isNullOrEmpty()) {
            jobProperties.application = configuration.application
        }
        return arrayListOf(jobProperties)
    }
}
