package _Self

import _Self.vcsRoots.*
import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.Project
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.githubConnection

object Project : Project({

    vcsRoot(Dp185DevOpsPRiS)

    features {
        githubConnection {
            id = "PROJECT_EXT_3"
            displayName = "GitHub.com"
            clientId = "66e0d23698e13d0315cd"
            clientSecret = "credentialsJSON:0d3d09fb-f846-4ad0-a927-daa2b5e5e21e"
        }
    }

    subProject(Application.Project)
    subProject(Terraform.Project)
})
