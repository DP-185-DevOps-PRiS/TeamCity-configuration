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
        step {
            name = "Save IP addresses"
            type = "ssh-deploy-runner"
            param("jetbrains.buildServer.deployer.sourcePath", """
                db_ip.txt
                vm_ip_priv.txt
                vm_ip_pub.txt
            """.trimIndent())
            param("jetbrains.buildServer.deployer.targetUrl", "~/IPs")
            param("jetbrains.buildServer.sshexec.authMethod", "DEFAULT_KEY")
            param("jetbrains.buildServer.deployer.ssh.transport", "jetbrains.buildServer.deployer.ssh.transport.scp")
        }
    }
})
