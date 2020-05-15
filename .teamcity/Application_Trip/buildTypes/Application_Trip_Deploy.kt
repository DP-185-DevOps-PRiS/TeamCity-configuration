package Application_Trip.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Trip_Deploy : BuildType({
    templates(Application.buildTypes.Application_Deploy)
    name = "Deploy"

    params {
        param("container", "trip")
    }

    dependencies {
        snapshot(Application_Trip_BuildPushImageToAcr) {
            runOnSameAgent = true
            onDependencyFailure = FailureAction.CANCEL
        }
    }
})
