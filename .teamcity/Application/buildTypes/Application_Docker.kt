package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.dockerSupport
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.dockerCommand

object Application_Docker : Template({
    name = "Docker"

    publishArtifacts = PublishMode.SUCCESSFUL

    params {
        password("login_server", "credentialsJSON:142c01de-b679-4822-8bd5-92ead4140cf1", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("LOGIN_SERVER", "credentialsJSON:142c01de-b679-4822-8bd5-92ead4140cf1", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    steps {
        dockerCommand {
            name = "Build image"
            id = "RUNNER_1"
            commandType = build {
                source = file {
                    path = "Dockerfile"
                }
                namesAndTags = "%SERVICE%"
                commandArgs = "--pull"
            }
            param("dockerImage.platform", "linux")
        }
        dockerCommand {
            name = "Tag image"
            id = "RUNNER_2"
            commandType = other {
                subCommand = "tag"
                commandArgs = "%SERVICE% %LOGIN_SERVER%/%SERVICE%"
            }
        }
        dockerCommand {
            name = "Push image"
            id = "RUNNER_3"
            commandType = push {
                namesAndTags = "%LOGIN_SERVER%/%SERVICE%"
            }
        }
    }

    features {
        dockerSupport {
            id = "DockerSupport"
            cleanupPushedImages = true
            loginToRegistry = on {
                dockerRegistryId = "PROJECT_EXT_5"
            }
        }
    }
})
