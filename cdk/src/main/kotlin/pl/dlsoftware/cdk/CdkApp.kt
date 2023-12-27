package pl.dlsoftware.cdk

import software.amazon.awscdk.App

class CdkApp: App() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val app = CdkApp()
            CdkStack(app, "GitHubRepoApplicationStack")
            app.synth()
        }
    }
}