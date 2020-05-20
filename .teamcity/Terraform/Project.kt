package Terraform

import Terraform.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Terraform")
    name = "Terraform"

    buildType(Terraform_InfrastructureCreation)
    buildType(Terraform_InfrastructureDestroying)

    params {
        password("env.ARM_SUBSCRIPTION_ID", "credentialsJSON:21757c12-89df-4120-b4d8-c701b7a6b42b", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.ARM_TENANT_ID", "credentialsJSON:3a8befbc-d61b-4c90-ac44-e813a0a459b1", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.ARM_CLIENT_ID", "credentialsJSON:7f676f25-1a15-4eac-81a3-ebc80417c816", display = ParameterDisplay.HIDDEN, readOnly = true)
        text("REPO", "Terraform", readOnly = true, allowEmpty = true)
        password("PROJECT_ID", "credentialsJSON:52eb176b-be65-4b1f-9363-137ea6254dd5", display = ParameterDisplay.HIDDEN, readOnly = true)
        password("env.ARM_CLIENT_SECRET", "credentialsJSON:53dbf002-6b58-46b5-b977-d6bd992c1b48", display = ParameterDisplay.HIDDEN, readOnly = true)
    }

    features {
        feature {
            id = "PROJECT_EXT_2"
            type = "CloudImage"
            param("subnet", "default")
            param("growingId", "false")
            param("agent_pool_id", "-2")
            param("source-id", "terraform-agent")
            param("network", "default")
            param("preemptible", "true")
            param("zone", "us-central1-a")
            param("profileId", "google-4")
            param("diskType", "pd-ssd")
            param("sourceImage", "terraform-agent")
            param("machineCustom", "false")
            param("maxInstances", "1")
            param("imageType", "Image")
            param("machineType", "n1-standard-1")
        }
        feature {
            id = "google-4"
            type = "CloudProfile"
            param("profileServerUrl", "http://10.128.0.3:8111")
            param("system.cloud.profile_id", "google-4")
            param("agent_pool_id", "-2")
            param("total-work-time", "")
            param("credentialsType", "key")
            param("description", "")
            param("cloud-code", "google")
            param("enabled", "true")
            param("agentPushPreset", "")
            param("profileId", "google-4")
            param("name", "Google-terraform-agent")
            param("next-hour", "")
            param("terminate-idle-time", "1")
            param("secure:accessKey", "credentialsJSON:1d60cf65-375e-40be-ae27-e374c8f2a8e3")
        }
    }
})
