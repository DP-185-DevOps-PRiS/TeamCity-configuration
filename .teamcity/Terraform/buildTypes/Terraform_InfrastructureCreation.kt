package Terraform.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.exec

object Terraform_InfrastructureCreation : BuildType({
    name = "Infrastructure creation"

    steps {
        exec {
            name = "Init"
            path = "terraform"
            arguments = "init"
        }
        exec {
            name = "Apply"
            path = "terraform"
            arguments = "apply"
        }
    }
})
