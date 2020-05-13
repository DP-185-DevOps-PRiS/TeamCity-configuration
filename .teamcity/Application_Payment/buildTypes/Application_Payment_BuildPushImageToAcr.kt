package Application_Payment.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Payment_BuildPushImageToAcr : BuildType({
    templates(Application.buildTypes.Application_Docker)
    name = "Build & Push image to ACR"

    params {
        param("service", "payment")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_6"
            buildType = "${Application_Payment_Packaging.id}"
            successfulOnly = true
        }
    }

    dependencies {
        dependency(Application_Payment_Packaging) {
            snapshot {
                runOnSameAgent = true
                onDependencyFailure = FailureAction.CANCEL
            }

            artifacts {
                id = "ARTIFACT_DEPENDENCY_13"
                artifactRules = """
                    *.jar => target
                    Dockerfile
                """.trimIndent()
            }
        }
    }
})
