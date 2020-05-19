package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Application_DeployAppOnTheNewServer : BuildType({
    name = "Deploy app on the new server"

    params {
        password("database_username", "credentialsJSON:cf6455f3-e3ea-471f-8248-9bd2f420a755", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("mail_password", "credentialsJSON:4ec2f34f-a6be-4705-b82d-f99fece188d4", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("send_mail", "credentialsJSON:689f6d3a-b429-4952-b808-aaa52d462995", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("ip_tc", "credentialsJSON:60cc8de1-2624-4259-b539-7063000c6f44", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("database_password", "credentialsJSON:bbb265da-3d7d-49c7-9a25-76240f4408d6", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("token", "credentialsJSON:455457a9-9c69-4051-a31b-c913395f4ebe", display = ParameterDisplay.HIDDEN, readOnly = true)
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
