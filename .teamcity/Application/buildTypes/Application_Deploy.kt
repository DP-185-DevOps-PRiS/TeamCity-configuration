package Application.buildTypes

import Application_Discovery.buildTypes.Application_Discovery_BuildPushImageToAcr
import Application_Gateway.buildTypes.Application_Gateway_BuildPushImageToAcr
import Application_Identity.buildTypes.Application_Identity_BuildPushImageToAcr
import Application_Messaging.buildTypes.Application_Messaging_BuildPushImageToAcr
import Application_Payment.buildTypes.Application_Payment_BuildPushImageToAcr
import Application_Simulation.buildTypes.Application_Simulation_BuildPushImageToAcr
import Application_Trip.buildTypes.Application_Trip_BuildPushImageToAcr
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
        finishBuildTrigger {
            buildType = "${Application_Identity_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
        finishBuildTrigger {
            buildType = "${Application_Messaging_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
        finishBuildTrigger {
            buildType = "${Application_Payment_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
        finishBuildTrigger {
            buildType = "${Application_Simulation_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
        finishBuildTrigger {
            buildType = "${Application_Trip_BuildPushImageToAcr.id}"
            successfulOnly = true
        }
    }
})
