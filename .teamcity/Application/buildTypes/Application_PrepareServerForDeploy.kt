package Application.buildTypes

import Terraform.buildTypes.Terraform_InfrastructureCreation
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.finishBuildTrigger

object Application_PrepareServerForDeploy : BuildType({
    name = "Prepare server for deploy"

    params {
        password("username_app", "credentialsJSON:da22594b-73c4-4496-afd6-71512fc907e7", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.IDENTITY_DB", "credentialsJSON:2139cf49-8d8d-4993-bbc3-45b20f110b04", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.SIMULATOR_DB", "credentialsJSON:ec8aa025-bbe0-4ab1-8457-a76b2f73cf84", display = ParameterDisplay.HIDDEN, readOnly = true)
        param("repo", "Deployment")
        password("database_username", "credentialsJSON:cf6455f3-e3ea-471f-8248-9bd2f420a755", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.MESSAGING_DB", "credentialsJSON:ef4276d4-5f3d-4b6d-92a4-579fa03364e1", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.PAYMENT_DB", "credentialsJSON:c9c6efdc-c2bf-4f47-871f-c2c2fae6e8a7", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("database_password", "credentialsJSON:bbb265da-3d7d-49c7-9a25-76240f4408d6", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("pub_key", "credentialsJSON:fd259e79-1bac-49f4-b650-7a8a1825fac8", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("token", "credentialsJSON:455457a9-9c69-4051-a31b-c913395f4ebe", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("secret_key", "credentialsJSON:38c2872a-77ec-4818-835b-62ea6642d340", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("mail_password", "credentialsJSON:4ec2f34f-a6be-4705-b82d-f99fece188d4", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("username_tc", "credentialsJSON:d7e1c6cc-bd2f-4ac0-879c-af7105fe4370", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.VEHICLE_DB", "credentialsJSON:21138bb2-8e3b-48ee-989d-a1179216fe08", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env_templates_path", "credentialsJSON:02a4c958-c2cb-4f38-9542-a2a57c6dd06f", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.TRIP_DB", "credentialsJSON:27c3ca79-1dd0-4242-9c9a-1c2fe711cd54", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("send_mail", "credentialsJSON:689f6d3a-b429-4952-b808-aaa52d462995", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("ip_tc", "credentialsJSON:60cc8de1-2624-4259-b539-7063000c6f44", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }

    steps {
        script {
            name = "Send env-files, deploy script and docker-compose.yml"
            scriptContent = """
                echo "Downloading current IPs ..."
                scp -i /root/.ssh/.tc/id_rsa -r %username_tc%@%ip_tc%:~/IPs .
                
                echo "Setting IP variables ..."
                IP_APP_PRIVATE=${'$'}( cat IPs/vm_ip_priv.txt )
                IP_DB=${'$'}( cat IPs/db_ip.txt )
                
                echo "Updating env-templates ..."
                mkdir env && chmod 700 env
                cp %env_templates_path%/kafka.env env
                sed "s|eureka|${'$'}IP_APP_PRIVATE|" %env_templates_path%/gateway-template.env > env/gateway.env
                sed "s|ip|${'$'}IP_DB|; s|DB|%env.IDENTITY_DB%|; s|database_username|%database_username%|; s|database_password|%database_password%|; s|eureka|${'$'}IP_APP_PRIVATE|" %env_templates_path%/identity-template.env > env/identity.env
                sed "s|ip|${'$'}IP_DB|; s|DB|%env.TRIP_DB%|; s|database_username|%database_username%|; s|database_password|%database_password%|; s|eureka|${'$'}IP_APP_PRIVATE|; s|token|%token%|" %env_templates_path%/trip-template.env > env/trip.env
                sed "s|ip|${'$'}IP_DB|; s|DB|%env.VEHICLE_DB%|; s|database_username|%database_username%|; s|database_password|%database_password%|; s|eureka|${'$'}IP_APP_PRIVATE|; s|token|%token%|" %env_templates_path%/vehicle-template.env > env/vehicle.env
                sed "s|ip|${'$'}IP_DB|; s|DB|%env.SIMULATOR_DB%|; s|database_username|%database_username%|; s|database_password|%database_password%|; s|eureka|${'$'}IP_APP_PRIVATE|; s|token|%token%|" %env_templates_path%/simulator-template.env > env/simulator.env
                sed "s|ip|${'$'}IP_DB|; s|DB|%env.PAYMENT_DB%|; s|database_username|%database_username%|; s|database_password|%database_password%|; s|pub_key|%pub_key%|; s|secret_key|%secret_key%|; s|eureka|${'$'}IP_APP_PRIVATE|" %env_templates_path%/payment-template.env > env/payment.env
                sed "s|send_mail|%send_mail%|; s|mail_password|%mail_password%|; s|ip|${'$'}IP_DB|; s|DB|%env.MESSAGING_DB%|; s|database_username|%database_username%|; s|database_password|%database_password%|; s|eureka|${'$'}IP_APP_PRIVATE|; s|token|%token%|" %env_templates_path%/messaging-template.env > env/messaging.env
                
                echo "Creating a directory for the applicaion ..."
                ssh %username_app%@${'$'}IP_APP_PRIVATE "sudo chown  %username_app% /opt && mkdir -p /opt/kickscooter"
                
                echo "Sending needed files ..."
                scp -r env deploy.sh docker-compose.yml %username_app%@${'$'}IP_APP_PRIVATE:/opt/kickscooter
            """.trimIndent()
        }
    }

    triggers {
        finishBuildTrigger {
            enabled = false
            buildType = "${Terraform_InfrastructureCreation.id}"
            successfulOnly = true
        }
    }
})
