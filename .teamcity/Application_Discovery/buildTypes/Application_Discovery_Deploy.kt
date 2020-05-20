package Application_Discovery.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Discovery_Deploy : BuildType({
    templates(Application.buildTypes.Application_Deploy)
    name = "Deploy"

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_17"
            buildType = "${Application_Discovery_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
    }

    dependencies {
        snapshot(Application_Discovery_BuildPushImageToAcr) {
            runOnSameAgent = true
            onDependencyFailure = FailureAction.CANCEL
        }
    }
})
