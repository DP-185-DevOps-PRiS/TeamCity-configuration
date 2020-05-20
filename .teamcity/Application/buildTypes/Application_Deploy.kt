package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Application_Deploy : Template({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1
    publishArtifacts = PublishMode.SUCCESSFUL

    params {
        password("URI_ACR", "credentialsJSON:142c01de-b679-4822-8bd5-92ead4140cf1", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("PASSWORD_ACR", "credentialsJSON:14665d28-7888-421c-9e5c-6854a9ec0b1f", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("PASSWORD_VM", "credentialsJSON:debd329a-523c-427a-969e-6c524b7bcfcb", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("USERNAME_TC", "credentialsJSON:d7e1c6cc-bd2f-4ac0-879c-af7105fe4370", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("USER_ACR", "credentialsJSON:c2eaf1bd-00bf-4e0f-8a65-2ee1800e5a57", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("IP_TC", "credentialsJSON:60cc8de1-2624-4259-b539-7063000c6f44", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("USERNAME_APP", "credentialsJSON:da22594b-73c4-4496-afd6-71512fc907e7", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    steps {
        script {
            name = "Run deploy.sh"
            id = "RUNNER_4"
            scriptContent = """
                #!/bin/bash
                echo "Downloading current IPs ..."
                scp -i /root/.ssh/.tc/id_rsa -r %USERNAME_TC%@%IP_TC%:~/IPs/AzureScaleSet .
                
                echo -n "Determining the number of servers ..."
                ip_address_list=()
                for file in IPs/AzureScaleSet/*.txt; do
                  ip=${'$'}( cat "${'$'}file" )
                  ip_address_list+=(${'$'}ip)
                done
                echo " ${'$'}{#ip_address_list[@]}"
                
                echo -n "Checking server health ..."
                health_servers=()
                for server in ${'$'}{ip_address_list[*]}; do
                  if [ ${'$'}(ping -c 4 ${'$'}server | grep -c -w "0% packet loss") -eq 1 ]; then
                    health_servers+=(${'$'}server) && echo -n " +"
                  else
                    echo -n " -"
                    ssh -i /root/.ssh/.tc/id_rsa %USERNAME_TC%@%IP_TC% "bash /root/IPs/AzureScaleSet/clean_up_old_ip.sh ${'$'}server"
                  fi
                done
                
                echo ""
                echo "Deploy ..."
                for IP in ${'$'}{health_servers[*]}; do
                  ssh %USERNAME_APP%@${'$'}IP "cd /opt/kickscooter && echo %PASSWORD_VM% | sudo -S docker login -u %USER_ACR% -p %PASSWORD_ACR% %URI_ACR% && echo %PASSWORD_VM% | sudo -S bash deploy.sh %CONTAINER%"
                done
            """.trimIndent()
        }
    }
})
