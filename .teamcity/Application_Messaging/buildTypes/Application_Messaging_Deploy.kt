package Application_Messaging.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Messaging_Deploy : BuildType({
    templates(Application.buildTypes.Application_Deploy)
    name = "Deploy"

    params {
        param("container", "messaging")
    }

    dependencies {
        snapshot(Application_Messaging_BuildPushImageToAcr) {
            runOnSameAgent = true
            onDependencyFailure = FailureAction.CANCEL
        }
    }
})
