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
                
                echo "Sending IPs ..."
                scp db_ip.txt vm_ip_pub.txt vm_ip_priv.txt %username_tc%@%ip_tc%:/root/IPs
            """.trimIndent()
        }
    }
})
