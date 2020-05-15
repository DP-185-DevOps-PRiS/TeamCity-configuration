package Application_Messaging

import Application_Messaging.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Application_Messaging")
    name = "Messaging"

    buildType(Application_Messaging_Packaging)
    buildType(Application_Messaging_BuildPushImageToAcr)
    buildType(Application_Messaging_Deploy)
})
