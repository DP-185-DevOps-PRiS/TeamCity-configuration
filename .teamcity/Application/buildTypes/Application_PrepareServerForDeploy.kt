package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Application_PrepareServerForDeploy : BuildType({
    name = "Prepare server for deploy"

    params {
        param("repo", "Deployment")
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }

    steps {
        script {
            name = "Update env-templates"
            scriptContent = """
                echo "Download current IPs"
                scp 
                IP_APP=${'$'}( cat IPs/vm_ip_pub.txt )
                IP_APP=${'$'}( cat IPs/vm_ip_pub.txt )
                ssh %username%@
            """.trimIndent()
        }
    }
})
