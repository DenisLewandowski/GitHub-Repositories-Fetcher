package pl.dlsoftware.cdk

import java.io.IOException
import org.junit.jupiter.api.Test
import software.amazon.awscdk.App
import software.amazon.awscdk.assertions.Template

class GitHubRepoBaseStackTest {

    @Test
    @Throws(IOException::class)
    fun testStack() {
        val app = App()
        val baseStack = GitHubRepoBaseStack(app, "test")
        val template = Template.fromStack(baseStack)
        template.resourceCountIs("AWS::EC2::VPC", 1)
        template.resourceCountIs("AWS::ECR::Repository", 1)
    }
}