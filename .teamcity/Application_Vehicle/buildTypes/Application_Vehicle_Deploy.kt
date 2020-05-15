package Application_Vehicle.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Vehicle_Deploy : BuildType({
    templates(Application.buildTypes.Application_Deploy)
    name = "Deploy"

    params {
        param("container", "vehicle")
    }

    dependencies {
        snapshot(Application_Vehicle_BuildPushImageToAcr) {
            runOnSameAgent = true
            onDependencyFailure = FailureAction.CANCEL
        }
    }
})
