package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_PrepareServerForDeploy : BuildType({
    name = "Prepare server for deploy"

    params {
        param("repo", "Deployment")
    }

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }
})
