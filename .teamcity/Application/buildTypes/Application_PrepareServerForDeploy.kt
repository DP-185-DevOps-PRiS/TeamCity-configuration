package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Application_PrepareServerForDeploy : BuildType({
    name = "Prepare server for deploy"

    params {
        password("database_username", "credentialsJSON:cf6455f3-e3ea-471f-8248-9bd2f420a755", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("mail_password", "credentialsJSON:4ec2f34f-a6be-4705-b82d-f99fece188d4", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("ip_tc", "credentialsJSON:60cc8de1-2624-4259-b539-7063000c6f44", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("database_password", "credentialsJSON:bbb265da-3d7d-49c7-9a25-76240f4408d6", display = ParameterDisplay.HIDDEN, readOnly = true)
        param("repo", "Deployment")
        password("env_templates_path", "credentialsJSON:02a4c958-c2cb-4f38-9542-a2a57c6dd06f", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }

    steps {
        script {
            name = "Send env-files, deploy script and docker-compose.yml"
            scriptContent = """
                echo "Downloading current IPs ..."
                scp -r %username_tc%@%ip_tc%:~/IPs .
                
                echo "Setting IP variables ..."
                IP_APP_PUBLIC=${'$'}( cat IPs/vm_ip_pub.txt )
                IP_APP_PRIVATE=${'$'}( cat IPs/vm_ip_priv.txt )
                IP_DB=${'$'}( cat IPs/db_ip.txt )
                
                echo "Updating env-templates ..."
                mkdir env && chmod 600 env
                cp %env_templates_path%/kafka.env env
                sed 's/eureka/${'$'}IP_APP_PRIVATE/' %env_templates_path%/gateway-template.env > env/gateway.env
                sed 's/ip/${'$'}IP_DB/; s/DB/identity/; s/database_username/%database_username%/; s/database_password/%database_password%/; s/eureka/${'$'}IP_APP_PRIVATE/' %env_templates_path%/identity-template.env > env/identity.env
                sed 's/ip/${'$'}IP_DB/; s/DB/trip/; s/database_username/%database_username%/; s/database_password/%database_password%/; s/eureka/${'$'}IP_APP_PRIVATE/; s/token/%token%/' %env_templates_path%/trip-template.env > env/trip.env
                sed 's/ip/${'$'}IP_DB/; s/DB/vehicle/; s/database_username/%database_username%/; s/database_password/%database_password%/; s/eureka/${'$'}IP_APP_PRIVATE/; s/token/%token%/' %env_templates_path%/vehicle-template.env > env/vehicle.env
                sed 's/ip/${'$'}IP_DB/; s/DB/simulator/; s/database_username/%database_username%/; s/database_password/%database_password%/; s/eureka/${'$'}IP_APP_PRIVATE/; s/token/%token%/' %env_templates_path%/simulator-template.env > env/simulator.env
                sed 's/ip/${'$'}IP_DB/; s/DB/payment/; s/database_username/%database_username%/; s/database_password/%database_password%/; s/pub_key/%pub_key%/; s/secret_key/%secret_key%/; s/eureka/${'$'}IP_APP_PRIVATE/' %env_templates_path%/payment-template.env > env/payment.env
                sed 's/send_mail/%send_mail%/; s/mail_password/%mail_password%/; s/ip/${'$'}IP_DB/; s/DB/messaging/; s/database_username/%database_username%/; s/database_password/%database_password%/; s/eureka/${'$'}IP_APP_PRIVATE/; s/token/%token%/' %env_templates_path%/messaging-template.env > env/messaging.env
                
                echo "Creating a directory for the applicaion ..."
                ssh %username_app%@${'$'}IP_APP_PUBLIC "sudo chown  %username_app% /opt && mkdir -p /opt/kickscooter"
                
                echo "Sending needed files ..."
                scp -r env deploy.sh docker-compose.yml %username_app%@${'$'}IP_APP_PUBLIC:/opt/kickscooter
            """.trimIndent()
        }
    }
})
