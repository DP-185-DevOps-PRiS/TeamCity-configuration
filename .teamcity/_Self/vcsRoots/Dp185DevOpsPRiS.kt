package _Self.vcsRoots

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

object Dp185DevOpsPRiS : GitVcsRoot({
    name = "DP-185-DevOps-PRiS"
    pollInterval = 10800
    url = "https://github.com/DP-185-DevOps-PRiS/%REPO%"
    branchSpec = "+:(master)"
})
