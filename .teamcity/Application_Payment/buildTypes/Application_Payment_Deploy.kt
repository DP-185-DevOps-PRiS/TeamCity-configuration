package Application_Payment.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Payment_Deploy : BuildType({
    templates(Application.buildTypes.Application_Deploy)
    name = "Deploy"

    params {
        param("container", "payment")
    }
})
