package Application_Identity.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven

object Application_Identity_Package : BuildType({
    name = "Package"

    artifactRules = """
        identity-service/target/*.jar
        identity-service/Dockerfile
        identity-starter/target/*.jar
    """.trimIndent()
    publishArtifacts = PublishMode.SUCCESSFUL

    params {
        param("REPO", "kick-scooter-identity")
        param("env.JDK_11_x64", "/usr/lib/jvm/java-11-openjdk-amd64")
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }

    steps {
        maven {
            name = "Package Starter"
            goals = "clean install"
            workingDir = "identity-starter"
            mavenVersion = custom {
                path = "/usr/share/maven"
            }
            jdkHome = "%env.JDK_11_x64%"
        }
        maven {
            name = "Package Service"
            goals = "clean package"
            workingDir = "identity-service"
            mavenVersion = custom {
                path = "/usr/share/maven"
            }
            jdkHome = "%env.JDK_11_x64%"
        }
        step {
            name = "Inspection of code quality"
            type = "sonar-plugin"
            param("teamcity.build.workingDir", "identity-service")
            param("sonarServer", "f1b3189f-52b1-4225-953d-6cfe2bd004d3")
        }
    }
})
