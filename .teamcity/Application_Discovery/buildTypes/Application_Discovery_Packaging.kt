package Application_Discovery.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Discovery_Packaging : BuildType({
    templates(Application.buildTypes.Application_PackagingTemplate)
    name = "Package"

    params {
        param("service", "discovery")
        param("repo", "kick-scooter-discovery")
    }
})
