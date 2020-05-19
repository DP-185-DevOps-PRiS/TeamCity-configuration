package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Application_DeployAppOnTheNewServer : BuildType({
    name = "Deploy app on the new server"

    steps {
        script {
            name = "Send env-files"
            scriptContent = """
                # Read new IP
                # Update env-files
                # Send them to the /opt/kickscooter
            """.trimIndent()
        }
    }
})
