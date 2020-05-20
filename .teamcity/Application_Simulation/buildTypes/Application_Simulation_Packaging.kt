package Application_Simulation.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Simulation_Packaging : BuildType({
    templates(Application.buildTypes.Application_PackagingTemplate)
    name = "Package"

    params {
        param("REPO", "kick-scooter-simulator")
    }
})
