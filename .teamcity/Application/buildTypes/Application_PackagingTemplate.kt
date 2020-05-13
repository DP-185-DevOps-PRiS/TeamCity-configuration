package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
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
    }
})
