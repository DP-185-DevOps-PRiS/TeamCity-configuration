package Application_Discovery

import Application_Discovery.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Application_Discovery")
    name = "Discovery"

    buildType(Application_Discovery_Packaging)
    buildType(Application_Discovery_BuildPushImageToAcr)
})
