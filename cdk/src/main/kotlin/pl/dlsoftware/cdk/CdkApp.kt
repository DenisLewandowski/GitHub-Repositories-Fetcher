package pl.dlsoftware.cdk

import software.amazon.awscdk.App

class CdkApp: App() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val app = CdkApp()
            val baseStack = GitHubRepoBaseStack(app, "GitHubRepoBaseStack")
            GitHubRepoApplicationStack(app, "GitHubRepoApplicationStack", baseStack.vpc, baseStack.ecr)
            app.synth()
        }
    }
}