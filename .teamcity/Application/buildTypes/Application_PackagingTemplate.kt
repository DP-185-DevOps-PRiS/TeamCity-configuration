package Application.buildTypes

import _Self.vcsRoots.Dp185DevOpsPRiS
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven

object Application_PackagingTemplate : Template({
    name = "Package"

    artifactRules = """
        target/*.jar
        Dockerfile
    """.trimIndent()
    publishArtifacts = PublishMode.SUCCESSFUL

    params {
        text("env.JDK_11_x64", "/usr/lib/jvm/java-11-openjdk-amd64", readOnly = true, allowEmpty = true)
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }

    steps {
        maven {
            name = "Package"
            id = "RUNNER_6"
            goals = "clean package"
            mavenVersion = custom {
                path = "/usr/share/maven"
            }
            jdkHome = "%env.JDK_11_x64%"
        }
        step {
            name = "Inspection of code quality"
            id = "RUNNER_8"
            type = "sonar-plugin"
            param("sonarServer", "f1b3189f-52b1-4225-953d-6cfe2bd004d3")
        }
    }

    features {
        commitStatusPublisher {
            id = "BUILD_EXT_1"
            vcsRootExtId = "${Dp185DevOpsPRiS.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "credentialsJSON:ecf91188-9a4b-4d29-87a5-ccb06b6fc589"
                }
            }
        }
    }
})
