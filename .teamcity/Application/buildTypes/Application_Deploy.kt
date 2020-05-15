package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Deploy : BuildType({
    name = "Deploy"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1
})
