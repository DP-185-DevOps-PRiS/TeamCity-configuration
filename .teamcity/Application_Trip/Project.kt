package Application_Trip

import Application_Trip.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Application_Trip")
    name = "Trip"

    buildType(Application_Trip_Deploy)
    buildType(Application_Trip_Packaging)
    buildType(Application_Trip_BuildPushImageToAcr)
})
