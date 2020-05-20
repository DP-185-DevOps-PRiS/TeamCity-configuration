package Application_Simulation.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Simulation_BuildPushImageToAcr : BuildType({
    templates(Application.buildTypes.Application_Docker)
    name = "Build & Push image to ACR"

    params {
        param("SERVICE", "simulator")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_3"
            buildType = "${Application_Simulation_Packaging.id}"
            successfulOnly = true
        }
    }

    dependencies {
        dependency(Application_Simulation_Packaging) {
            snapshot {
                runOnSameAgent = true
                onDependencyFailure = FailureAction.CANCEL
            }

            artifacts {
                id = "ARTIFACT_DEPENDENCY_9"
                artifactRules = """
                    *.jar => target
                    Dockerfile
                """.trimIndent()
            }
        }
    }
})
