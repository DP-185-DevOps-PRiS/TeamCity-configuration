package Application_Vehicle.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Vehicle_Deploy : BuildType({
    templates(Application.buildTypes.Application_Deploy)
    name = "Deploy"

    params {
        param("CONTAINER", "vehicle")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_24"
            buildType = "${Application_Vehicle_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
    }

    dependencies {
        snapshot(Application_Vehicle_BuildPushImageToAcr) {
            runOnSameAgent = true
            onDependencyFailure = FailureAction.CANCEL
        }
    }
})
