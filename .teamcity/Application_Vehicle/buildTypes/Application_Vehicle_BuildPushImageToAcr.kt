package Application_Vehicle.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Vehicle_BuildPushImageToAcr : BuildType({
    templates(Application.buildTypes.Application_Docker)
    name = "Build & Push image to ACR"

    params {
        param("service", "vehicle")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_8"
            buildType = "${Application_Vehicle_Packaging.id}"
            successfulOnly = true
        }
    }

    dependencies {
        dependency(Application_Vehicle_Packaging) {
            snapshot {
                runOnSameAgent = true
                onDependencyFailure = FailureAction.CANCEL
            }

            artifacts {
                id = "ARTIFACT_DEPENDENCY_15"
                artifactRules = """
                    *.jar => target
                    Dockerfile
                """.trimIndent()
            }
        }
    }
})
