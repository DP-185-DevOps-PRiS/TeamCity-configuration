package Application_Gateway.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Gateway_BuildPushImageToAcr : BuildType({
    templates(Application.buildTypes.Application_Docker)
    name = "Build & Push image to ACR"

    params {
        param("SERVICE", "gateway")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_2"
            buildType = "${Application_Gateway_Packaging.id}"
            successfulOnly = true
        }
    }

    dependencies {
        dependency(Application_Gateway_Packaging) {
            snapshot {
                runOnSameAgent = true
                onDependencyFailure = FailureAction.CANCEL
            }

            artifacts {
                id = "ARTIFACT_DEPENDENCY_8"
                artifactRules = """
                    *.jar => target
                    Dockerfile
                """.trimIndent()
            }
        }
    }
})
