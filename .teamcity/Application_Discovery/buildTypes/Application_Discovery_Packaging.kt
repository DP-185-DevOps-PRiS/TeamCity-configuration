package Application_Discovery.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Discovery_Packaging : BuildType({
    templates(Application.buildTypes.Application_PackagingTemplate)
    name = "Package"

    params {
        param("REPO", "kick-scooter-discovery")
    }
})
