package Application_Payment.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.MavenBuildStep
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.exec
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.maven

object Application_Payment_Packaging : BuildType({
    templates(Application.buildTypes.Application_PackagingTemplate)
    name = "Package"

    params {
        param("REPO", "kick-scooter-payment")
        param("service", "payment")
    }

    steps {
        exec {
            name = "Install identity-starter dependency"
            id = "RUNNER_11"
            path = "mvn"
            arguments = "install:install-file -Dfile=identity-starter-0.0.1.jar -DgroupId=com.softserve -DartifactId=identity-starter -Dversion=0.0.1 -Dpackaging=jar"
        }
        maven {
            name = "Package"
            id = "RUNNER_6"
            goals = "clean package"
            mavenVersion = custom {
                path = "/usr/share/maven"
            }
            localRepoScope = MavenBuildStep.RepositoryScope.MAVEN_DEFAULT
            jdkHome = "%env.JDK_11_x64%"
        }
        stepsOrder = arrayListOf("RUNNER_11", "RUNNER_6")
    }

    dependencies {
        artifacts(Application_Identity.buildTypes.Application_Identity_Package) {
            id = "ARTIFACT_DEPENDENCY_16"
            buildRule = lastSuccessful()
            artifactRules = "identity-starter-*.jar"
        }
    }
})
