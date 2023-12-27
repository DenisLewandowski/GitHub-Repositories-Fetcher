package pl.dlsoftware.cdk

import java.io.IOException
import org.junit.jupiter.api.Test
import software.amazon.awscdk.App
import software.amazon.awscdk.assertions.Template

class GitHubRepoApplicationStackTest {

    @Test
    @Throws(IOException::class)
    fun testStack() {
        val app = App()
        val baseStack = GitHubRepoBaseStack(app, "base-test")
        val stack = GitHubRepoApplicationStack(app, "test", baseStack.vpc, baseStack.ecr)
        val template = Template.fromStack(stack)
        template.resourceCountIs("AWS::ECS::Cluster", 1)
        template.resourceCountIs("AWS::ECS::Service", 1)
        template.resourceCountIs("AWS::ECS::TaskDefinition", 1)
        template.resourceCountIs("AWS::ECS::TaskDefinition", 1)
        template.resourceCountIs("AWS::ElasticLoadBalancingV2::TargetGroup", 1)
        template.resourceCountIs("AWS::ElasticLoadBalancingV2::LoadBalancer", 1)
        template.resourceCountIs("AWS::ElasticLoadBalancingV2::Listener", 1)
        template.resourceCountIs("AWS::ApiGateway::RestApi", 1)
        template.resourceCountIs("AWS::ApiGateway::Deployment", 1)
        template.resourceCountIs("AWS::ApiGateway::Stage", 1)
    }
}