package Application_Identity.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_Identity_BuildPushImageToAcr : BuildType({
    templates(Application.buildTypes.Application_Docker)
    name = "Build & Push image to ACR"

    params {
        param("service", "identity")
    }

    triggers {
        finishBuildTrigger {
            id = "TRIGGER_4"
            buildType = "${Application_Identity_Package.id}"
            successfulOnly = true
        }
    }

    dependencies {
        dependency(Application_Identity_Package) {
            snapshot {
                runOnSameAgent = true
                onDependencyFailure = FailureAction.CANCEL
            }

            artifacts {
                id = "ARTIFACT_DEPENDENCY_10"
                artifactRules = """
                    identity-service-*.jar => target
                    Dockerfile
                """.trimIndent()
            }
        }
    }
})
