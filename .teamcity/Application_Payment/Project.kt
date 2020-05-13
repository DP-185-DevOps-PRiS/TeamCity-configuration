package Application_Payment

import Application_Payment.buildTypes.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project

object Project : Project({
    id("Application_Payment")
    name = "Payment"

    buildType(Application_Payment_Packaging)
    buildType(Application_Payment_BuildPushImageToAcr)
})
