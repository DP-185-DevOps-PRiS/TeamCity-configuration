package Application_Simulation.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Simulation_Deploy : BuildType({
    templates(Application.buildTypes.Application_Deploy)
    name = "Deploy"

    params {
        param("container", "simulator")
    }

    dependencies {
        snapshot(Application_Simulation_BuildPushImageToAcr) {
            runOnSameAgent = true
            onDependencyFailure = FailureAction.CANCEL
        }
    }
})
