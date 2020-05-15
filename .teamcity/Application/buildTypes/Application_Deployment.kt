package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Deployment : Template({
    name = "Deployment"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    steps {
        step {
            name = "Run deploy.sh"
            id = "RUNNER_8"
            type = "ssh-exec-runner"
            param("jetbrains.buildServer.deployer.username", "%username%")
            param("jetbrains.buildServer.sshexec.command", """
                sudo mv /tmp/env /opt/kickscooter
                sudo mv /tmp/deploy.sh /opt/kickscooter
                sudo mv /tmp/docker-compose.yml /opt/kickscooter
                cd /opt/kickscooter
                sudo bash deploy.sh
            """.trimIndent())
            param("jetbrains.buildServer.deployer.targetUrl", "%vm_ip%")
            param("jetbrains.buildServer.sshexec.authMethod", "UPLOADED_KEY")
        }
    }
})
