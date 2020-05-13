package Terraform.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.exec

object Terraform_InfrastructureDestroying : BuildType({
    name = "Infrastructure destroying"

    steps {
        exec {
            name = "Destroy"
            path = "terraform"
            arguments = "destroy"
        }
    }
})
