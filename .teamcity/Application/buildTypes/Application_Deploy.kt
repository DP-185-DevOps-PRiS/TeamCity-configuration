package Application.buildTypes

import Application_Discovery.buildTypes.Application_Discovery_BuildPushImageToAcr
import Application_Gateway.buildTypes.Application_Gateway_BuildPushImageToAcr
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    triggers {
        finishBuildTrigger {
            buildType = "${Application_Discovery_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
        finishBuildTrigger {
            buildType = "${Application_Gateway_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
    }
})
