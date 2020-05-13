package Application_Vehicle

import Application_Vehicle.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Application_Vehicle")
    name = "Vehicle"

    buildType(Application_Vehicle_BuildPushImageToAcr)
    buildType(Application_Vehicle_Packaging)
})
