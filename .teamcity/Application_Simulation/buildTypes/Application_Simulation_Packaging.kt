package Application_Simulation.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Simulation_Packaging : BuildType({
    templates(Application.buildTypes.Application_PackagingTemplate)
    name = "Package"

    params {
        param("service", "simulator")
        param("repo", "kick-scooter-simulator")
    }
})
