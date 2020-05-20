package Application_Simulation.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Simulation_Deploy : BuildType({
    templates(Application.buildTypes.Application_Deploy)
    name = "Deploy"

    params {
        param("CONTAINER", "simulator")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_22"
            buildType = "${Application_Simulation_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
    }

    dependencies {
        snapshot(Application_Simulation_BuildPushImageToAcr) {
            runOnSameAgent = true
            onDependencyFailure = FailureAction.CANCEL
        }
    }
})
