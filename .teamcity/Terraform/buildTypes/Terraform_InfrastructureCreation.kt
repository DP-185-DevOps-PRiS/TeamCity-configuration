package Terraform.buildTypes

import _Self.vcsRoots.Dp185DevOpsPRiS
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Terraform_InfrastructureCreation : BuildType({
    name = "Create infrastructure"

    params {
        password("env.ARM_SUBSCRIPTION_ID", "credentialsJSON:21757c12-89df-4120-b4d8-c701b7a6b42b", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.ARM_TENANT_ID", "credentialsJSON:3a8befbc-d61b-4c90-ac44-e813a0a459b1", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.ARM_CLIENT_ID", "credentialsJSON:7f676f25-1a15-4eac-81a3-ebc80417c816", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("username_tc", "credentialsJSON:d7e1c6cc-bd2f-4ac0-879c-af7105fe4370", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("ip_tc", "credentialsJSON:60cc8de1-2624-4259-b539-7063000c6f44", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.ARM_CLIENT_SECRET", "credentialsJSON:53dbf002-6b58-46b5-b977-d6bd992c1b48", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("BUCKET_NAME", "credentialsJSON:e2f62c8d-69c1-4358-a022-60a63b2d7c77", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }

    steps {
        script {
            name = "Init & Apply"
            workingDir = "terraform_infrastructure"
            scriptContent = """
                cp %PROJECT_ID% .
                
                terraform init -backend=true
                terraform apply -auto-approve
                
                echo "Update env-files ..."
                service_list=(gateway identity messaging payment simulator trip vehicle)
                DB_IP=${'$'}( cat dp_ip.txt )
                mkdir env && chmod 700 env
                cp %ENV_TEMPLATES_PATH%/kafka.env env
                for service in ${'$'}{service_list[*]}; do
                  sed "s|ip|${'$'}DB_IP|" %ENV_TEMPLATES_PATH%/${'$'}{service}-template.env > env/${'$'}{service}.env
                done
                
                echo "Sending updated env-files to the Google Storage ..."
                gsutil -q cp -r env/ gs://%BUCKET_NAME%/
            """.trimIndent()
        }
    }

    triggers {
        vcs {
            enabled = false
        }
    }

    features {
        commitStatusPublisher {
            vcsRootExtId = "${Dp185DevOpsPRiS.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = personalToken {
                    token = "credentialsJSON:ecf91188-9a4b-4d29-87a5-ccb06b6fc589"
                }
            }
        }
    }
})
