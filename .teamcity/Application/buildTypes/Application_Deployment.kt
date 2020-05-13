package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Deployment : Template({
    name = "Deployment"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    params {
        password("vm_ip", "credentialsJSON:607df04b-90b3-4584-b210-d63c0f6ea59b", display = ParameterDisplay.HIDDEN, readOnly = true)
        param("repo", "Deployment")
        password("username", "credentialsJSON:da22594b-73c4-4496-afd6-71512fc907e7", display = ParameterDisplay.HIDDEN, readOnly = true)
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
    }
})
