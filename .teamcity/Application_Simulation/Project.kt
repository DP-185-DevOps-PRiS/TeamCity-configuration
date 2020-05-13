package Application_Simulation

import Application_Simulation.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Application_Simulation")
    name = "Simulator"

    buildType(Application_Simulation_BuildPushImageToAcr)
    buildType(Application_Simulation_Packaging)
})
