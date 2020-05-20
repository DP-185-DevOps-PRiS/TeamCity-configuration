package Application_Gateway.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Gateway_Packaging : BuildType({
    templates(Application.buildTypes.Application_PackagingTemplate)
    name = "Package"

    params {
        param("REPO", "kick-scooter-gateway")
        param("service", "gateway")
    }
})
