package Terraform.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Terraform_InfrastructureCreation : BuildType({
    name = "Create infrastructure"

    params {
        password("env.SUB_ID", "credentialsJSON:21757c12-89df-4120-b4d8-c701b7a6b42b", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.CLIENT_ID", "credentialsJSON:7f676f25-1a15-4eac-81a3-ebc80417c816", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.TENANT_ID", "credentialsJSON:3a8befbc-d61b-4c90-ac44-e813a0a459b1", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("ip_tc", "credentialsJSON:60cc8de1-2624-4259-b539-7063000c6f44", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("username_tc", "credentialsJSON:d7e1c6cc-bd2f-4ac0-879c-af7105fe4370", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.CLIENT_SECRET", "credentialsJSON:53dbf002-6b58-46b5-b977-d6bd992c1b48", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }

    steps {
        script {
            name = "Init & Apply"
            workingDir = "terraform_infrastructure"
            scriptContent = """
                terraform init -backend=true
                terraform apply -auto-approve
                
                echo "Sending IPs ..."
                scp db_ip.txt vm_ip_priv.txt %username_tc%@%ip_tc%:/root/IPs
            """.trimIndent()
        }
    }
})
