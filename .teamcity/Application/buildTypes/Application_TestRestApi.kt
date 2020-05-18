package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*

object Application_TestRestApi : BuildType({
    name = "TestRestApi"

    vcs {
        root(_Self.vcsRoots.Dp185DevOpsPRiS)
    }
})
