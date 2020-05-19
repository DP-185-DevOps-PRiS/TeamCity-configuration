package Application.buildTypes

import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script

object Application_TestRestApi : BuildType({
    name = "TestRestApi"

    steps {
        script {
            name = "I have been executed by REST request"
            scriptContent = """
                if [ ${'$'}whoami == root ]; then
                  echo "Hi, root."
                else
                  echo "I don't know you."
                fi
                echo "The end."
            """.trimIndent()
        }
    }
})
