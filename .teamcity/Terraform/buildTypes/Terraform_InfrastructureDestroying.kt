package Terraform.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Terraform_InfrastructureDestroying : BuildType({
    name = "Destroy infrastructure"

    params {
        password("env.ARM_CLIENT_ID", "credentialsJSON:7f676f25-1a15-4eac-81a3-ebc80417c816", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }

    steps {
        script {
            name = "Destroy"
            workingDir = "terraform_infrastructure"
            scriptContent = """
                cp /root/gcp/fourthdemo-274718-829977cd6967.json .
                
                terraform init -backend=true
                terraform destroy -auto-approve
            """.trimIndent()
        }
    }
})
