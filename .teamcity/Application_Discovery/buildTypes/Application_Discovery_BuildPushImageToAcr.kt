package Application_Discovery.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Discovery_BuildPushImageToAcr : BuildType({
    templates(Application.buildTypes.Application_Docker)
    name = "Build & Push image to ACR"

    params {
        param("SERVICE", "discovery")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_1"
            buildType = "${Application_Discovery_Packaging.id}"
            successfulOnly = true
        }
    }

    dependencies {
        dependency(Application_Discovery_Packaging) {
            snapshot {
                runOnSameAgent = true
                onDependencyFailure = FailureAction.CANCEL
            }

            artifacts {
                id = "ARTIFACT_DEPENDENCY_7"
                artifactRules = """
                    *.jar => target
                    Dockerfile
                """.trimIndent()
            }
        }
    }
})
