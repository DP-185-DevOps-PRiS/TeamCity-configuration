package Application_Identity

import Application_Identity.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Application_Identity")
    name = "Identity"

    buildType(Application_Identity_BuildPushImageToAcr)
    buildType(Application_Identity_Package)
    buildType(Application_Identity_Deploy)
})
