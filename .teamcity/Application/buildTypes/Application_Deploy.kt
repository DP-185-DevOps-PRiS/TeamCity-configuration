package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Application_Deploy : Template({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    params {
        password("server", "credentialsJSON:142c01de-b679-4822-8bd5-92ead4140cf1", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("username_app", "credentialsJSON:da22594b-73c4-4496-afd6-71512fc907e7", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("password", "credentialsJSON:14665d28-7888-421c-9e5c-6854a9ec0b1f", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("ip_tc", "credentialsJSON:60cc8de1-2624-4259-b539-7063000c6f44", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("username_tc", "credentialsJSON:d7e1c6cc-bd2f-4ac0-879c-af7105fe4370", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("user", "credentialsJSON:c2eaf1bd-00bf-4e0f-8a65-2ee1800e5a57", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    steps {
        script {
            name = "Run deploy.sh"
            id = "RUNNER_4"
            scriptContent = """
                echo "Downloading current IPs ..."
                scp -i /root/.ssh/.tc/id_rsa -r %username_tc%@%ip_tc%:~/IPs/vm_ip_priv.txt .
                
                echo "Setting IP variables ..."
                IP_APP_PRIVATE=${'$'}( cat vm_ip_priv.txt )
                
                echo "Run deploy.sh ..."
                ssh %username_app%@${'$'}IP_APP_PRIVATE "cd /opt/kickscooter && sudo docker login -u %user% -p %password% %server% && sudo bash deploy.sh %container%"
            """.trimIndent()
        }
    }
})
