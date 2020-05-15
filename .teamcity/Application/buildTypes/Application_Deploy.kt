package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Application_Deploy : Template({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    steps {
        script {
            name = "Run deploy.sh"
            id = "RUNNER_4"
            scriptContent = """
                echo "Downloading current IPs ..."
                scp -i /root/.ssh/.tc/id_rsa -r %username_tc%@%ip_tc%:~/IPs/vm_ip_pub.txt .
                
                echo "Setting IP variables ..."
                IP_APP_PUBLIC=${'$'}( cat IPs/vm_ip_pub.txt )
                
                echo "Run deploy.sh ..."
                ssh %username_app%@${'$'}IP_APP_PUBLIC "cd /opt/kickscooter && sudo bash deploy.sh %container%"
            """.trimIndent()
        }
    }
})
