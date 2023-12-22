package pl.dlsoftware.cdk

import software.amazon.awscdk.App

object CdkApp {
    @JvmStatic
    fun main(args: Array<String>) {
        val app = App()
        CdkStack(app, "CdkStack")
        app.synth()
    }
}