package pl.dlsoftware.cdk

import java.io.IOException
import org.junit.jupiter.api.Test
import software.amazon.awscdk.App
import software.amazon.awscdk.assertions.Template

class CdkStackTest {

    @Test
    @Throws(IOException::class)
    fun testStack() {
        val app = App()
        val stack = CdkStack(app, "test")
        val template = Template.fromStack(stack)
        template.hasResourceProperties("AWS::SQS::Queue", mapOf("VisibilityTimeout" to 300))
        template.resourceCountIs("AWS::SNS::Topic", 1)
    }
}