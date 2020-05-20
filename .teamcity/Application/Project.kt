package Application

import Application.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.dockerRegistry

object Project : Project({
    id("Application")
    name = "Application"

    template(Application_PackagingTemplate)
    template(Application_Docker)
    template(Application_Deploy)

    features {
        feature {
            id = "PROJECT_EXT_4"
            type = "sonar-qube"
            param("password", "zxxe39e3f3df6ad3740a7850d6d8e026262")
            param("name", "GCP SonarQube")
            param("id", "f1b3189f-52b1-4225-953d-6cfe2bd004d3")
            param("login", "admin")
            param("url", "http://10.138.0.3/")
        }
        dockerRegistry {
            id = "PROJECT_EXT_5"
            name = "Azure Container Registry"
            url = "kickscooter.azurecr.io"
            userName = "kickscooter"
            password = "credentialsJSON:14665d28-7888-421c-9e5c-6854a9ec0b1f"
        }
        feature {
            id = "PROJECT_EXT_6"
            type = "CloudImage"
            param("subnet", "default")
            param("growingId", "true")
            param("agent_pool_id", "-2")
            param("source-id", "application-agent")
            param("network", "default")
            param("preemptible", "true")
            param("zone", "us-central1-a")
            param("profileId", "google-3")
            param("diskType", "pd-ssd")
            param("sourceImage", "application-agent")
            param("machineCustom", "false")
            param("maxInstances", "3")
            param("imageType", "Image")
            param("machineType", "n1-standard-1")
        }
        feature {
            id = "google-3"
            type = "CloudProfile"
            param("profileServerUrl", "http://10.128.0.3:8111")
            param("system.cloud.profile_id", "google-3")
            param("total-work-time", "")
            param("credentialsType", "key")
            param("description", "")
            param("cloud-code", "google")
            param("enabled", "true")
            param("agentPushPreset", "")
            param("profileId", "google-3")
            param("name", "Google-app-agents")
            param("next-hour", "")
            param("terminate-idle-time", "3")
            param("secure:accessKey", "credentialsJSON:1d60cf65-375e-40be-ae27-e374c8f2a8e3")
        }
    }

    cleanup {
        keepRule {
            id = "KEEP_RULE_1"
            keepAtLeast = allBuilds()
            dataToKeep = historyAndStatistics {
                preserveLogs = true
            }
            applyPerEachBranch = true
            preserveArtifactsDependencies = true
        }
    }

    subProject(Application_Discovery.Project)
    subProject(Application_Messaging.Project)
    subProject(Application_Simulation.Project)
    subProject(Application_Vehicle.Project)
    subProject(Application_Gateway.Project)
    subProject(Application_Payment.Project)
    subProject(Application_Identity.Project)
    subProject(Application_Trip.Project)
})
