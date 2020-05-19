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
        password("PROJECT_ID", "credentialsJSON:52eb176b-be65-4b1f-9363-137ea6254dd5", display = ParameterDisplay.HIDDEN, readOnly = true)
        text("repo", "Terraform", readOnly = true, allowEmpty = true)
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
