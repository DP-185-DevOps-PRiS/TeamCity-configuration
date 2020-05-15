package Application_Identity.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Identity_Deploy : BuildType({
    templates(Application.buildTypes.Application_Deploy)
    name = "Deploy"

    params {
        param("container", "identity")
    }

    dependencies {
        snapshot(Application_Identity_BuildPushImageToAcr) {
            runOnSameAgent = true
            onDependencyFailure = FailureAction.CANCEL
        }
    }
})
