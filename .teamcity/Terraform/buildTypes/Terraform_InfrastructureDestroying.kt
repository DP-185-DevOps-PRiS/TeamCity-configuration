package Terraform.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Terraform_InfrastructureDestroying : BuildType({
    name = "Destroy infrastructure"

    params {
        password("env.ARM_SUBSCRIPTION_ID", "credentialsJSON:21757c12-89df-4120-b4d8-c701b7a6b42b", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.ARM_TENANT_ID", "credentialsJSON:3a8befbc-d61b-4c90-ac44-e813a0a459b1", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.ARM_CLIENT_SECRET", "credentialsJSON:53dbf002-6b58-46b5-b977-d6bd992c1b48", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }

    steps {
        script {
            name = "Destroy"
            workingDir = "terraform_infrastructure"
            scriptContent = """
                cp %PROJECT_ID% .
                terraform init -backend=true
                terraform destroy -auto-approve
            """.trimIndent()
        }
    }
})
