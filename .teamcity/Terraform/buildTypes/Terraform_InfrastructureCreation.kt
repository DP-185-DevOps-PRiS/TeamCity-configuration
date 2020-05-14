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
            arguments = "apply -auto-approve"
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
