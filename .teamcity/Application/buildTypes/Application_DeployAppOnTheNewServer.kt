package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Application_DeployAppOnTheNewServer : BuildType({
    name = "Deploy app on the new server"

    params {
        password("database_password", "credentialsJSON:bbb265da-3d7d-49c7-9a25-76240f4408d6", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    steps {
        script {
            name = "Send env-files"
            scriptContent = """
                # Read new IP
                # Update env-files
                # Send them to the /opt/kickscooter
            """.trimIndent()
        }
        script {
            name = "Run deploy.sh"
            scriptContent = "# Run script which execute deploy.sh with service param. at the end"
        }
    }
})
