package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Application_Deploy : Template({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    params {
        password("username_tc", "credentialsJSON:d7e1c6cc-bd2f-4ac0-879c-af7105fe4370", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("username_app", "credentialsJSON:da22594b-73c4-4496-afd6-71512fc907e7", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("ip_tc", "credentialsJSON:60cc8de1-2624-4259-b539-7063000c6f44", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    steps {
        script {
            name = "Run deploy.sh"
            id = "RUNNER_4"
            scriptContent = """
                echo "Downloading current IPs ..."
                scp -i /root/.ssh/.tc/id_rsa -r %username_tc%@%ip_tc%:~/IPs/vm_ip_priv.txt .
                
                echo "Setting IP variables ..."
                IP_APP_PUBLIC=${'$'}( cat vm_ip_priv.txt )
                
                echo "Run deploy.sh ..."
                ssh %username_app%@${'$'}IP_APP_PRIVATE "cd /opt/kickscooter && sudo bash deploy.sh %container%"
            """.trimIndent()
        }
    }
})
