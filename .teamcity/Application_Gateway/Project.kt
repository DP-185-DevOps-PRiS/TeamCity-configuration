package Application_Gateway

import Application_Gateway.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Application_Gateway")
    name = "Gateway"

    buildType(Application_Gateway_Packaging)
    buildType(Application_Gateway_BuildPushImageToAcr)
})
