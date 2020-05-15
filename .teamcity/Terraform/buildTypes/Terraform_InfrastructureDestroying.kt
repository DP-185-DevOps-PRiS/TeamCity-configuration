package Terraform.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.exec

object Terraform_InfrastructureDestroying : BuildType({
    name = "Destroy infrastructure"

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }

    steps {
        exec {
            name = "Destroy"
            workingDir = "terraform_infrastructure"
            path = "terraform"
            arguments = "destroy"
        }
    }
})
