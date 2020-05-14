package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Deployment : Template({
    name = "Deployment"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    params {
        param("repo", "Deployment")
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }

    steps {
        step {
            name = "Upload needed files"
            id = "RUNNER_7"
            type = "ssh-deploy-runner"
            param("jetbrains.buildServer.deployer.username", "%username%")
            param("jetbrains.buildServer.deployer.sourcePath", """
                ~/env
                deploy.sh
                docker-compose.yml
            """.trimIndent())
            param("jetbrains.buildServer.deployer.targetUrl", "%vm_ip%:/tmp")
            param("jetbrains.buildServer.sshexec.authMethod", "DEFAULT_KEY")
            param("jetbrains.buildServer.deployer.ssh.transport", "jetbrains.buildServer.deployer.ssh.transport.scp")
        }
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
