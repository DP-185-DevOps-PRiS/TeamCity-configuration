package Application_Messaging.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Messaging_Deploy : BuildType({
    templates(Application.buildTypes.Application_Deploy)
    name = "Deploy"

    params {
        param("container", "messaging")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_20"
            buildType = "${Application_Messaging_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
    }

    dependencies {
        snapshot(Application_Messaging_BuildPushImageToAcr) {
            runOnSameAgent = true
            onDependencyFailure = FailureAction.CANCEL
        }
    }
})
