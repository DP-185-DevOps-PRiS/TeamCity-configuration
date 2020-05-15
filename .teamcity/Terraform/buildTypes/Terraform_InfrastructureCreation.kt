package Terraform.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Terraform_InfrastructureCreation : BuildType({
    name = "Create infrastructure"

    steps {
        script {
            name = "Init & Apply"
            scriptContent = """
                terraform init
                terraform apply -auto-approve
            """.trimIndent()
        }
    }
})
