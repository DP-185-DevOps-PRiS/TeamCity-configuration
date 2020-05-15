package Terraform.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Terraform_InfrastructureCreation : BuildType({
    name = "Create infrastructure"

    params {
        password("ip_tc", "credentialsJSON:60cc8de1-2624-4259-b539-7063000c6f44", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    steps {
        script {
            name = "Init & Apply"
            scriptContent = """
                terraform init
                terraform apply -auto-approve
                
                echo "Sending IPs ..."
                scp db_ip.txt vm_ip_pub.txt vm_ip_priv.txt %username_tc%@%ip_tc%:/root/IPs
            """.trimIndent()
        }
    }
})
