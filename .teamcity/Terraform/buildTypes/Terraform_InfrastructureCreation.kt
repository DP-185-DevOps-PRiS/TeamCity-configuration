package Terraform.buildTypes

import _Self.vcsRoots.Dp185DevOpsPRiS
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

object Terraform_InfrastructureCreation : BuildType({
    name = "Create infrastructure"

    params {
        password("BUCKET_NAME", "credentialsJSON:e2f62c8d-69c1-4358-a022-60a63b2d7c77", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("ENV_TEMPLATES_PATH", "credentialsJSON:c835b3bb-4e19-40cc-ac0f-e119f3e93afe", display = ParameterDisplay.HIDDEN, readOnly = true)
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
                DB_IP=${'$'}( cat db_ip.txt )
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
