package Application_Messaging.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Messaging_BuildPushImageToAcr : BuildType({
    templates(Application.buildTypes.Application_Docker)
    name = "Build & Push image to ACR"

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_5"
            buildType = "${Application_Messaging_Packaging.id}"
            successfulOnly = true
        }
    }

    dependencies {
        dependency(Application_Messaging_Packaging) {
            snapshot {
                runOnSameAgent = true
                onDependencyFailure = FailureAction.CANCEL
            }

            artifacts {
                id = "ARTIFACT_DEPENDENCY_12"
                artifactRules = """
                    *.jar => target
                    Dockerfile
                """.trimIndent()
            }
        }
    }
})
