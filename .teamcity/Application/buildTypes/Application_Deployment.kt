package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_Deployment : Template({
    name = "Deployment"

    enablePersonalBuilds = false
    type = BuildTypeSettings.Type.DEPLOYMENT
    maxRunningBuilds = 1

    params {
        param("repo", "Deployment")
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }
})
