package Application_Trip.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Trip_Deploy : BuildType({
    templates(Application.buildTypes.Application_Deploy)
    name = "Deploy"

    params {
        param("container", "trip")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_23"
            buildType = "${Application_Trip_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
    }

    dependencies {
        snapshot(Application_Trip_BuildPushImageToAcr) {
            runOnSameAgent = true
            onDependencyFailure = FailureAction.CANCEL
        }
    }
})
