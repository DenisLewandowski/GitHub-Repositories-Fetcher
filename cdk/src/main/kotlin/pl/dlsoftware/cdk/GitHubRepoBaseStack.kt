package pl.dlsoftware.cdk

import software.amazon.awscdk.RemovalPolicy
import software.amazon.awscdk.Stack
import software.amazon.awscdk.StackProps
import software.amazon.awscdk.services.ec2.Vpc
import software.amazon.awscdk.services.ecr.Repository
import software.constructs.Construct

class GitHubRepoBaseStack constructor(
    parent: Construct,
    id: String?,
    props: StackProps? = null) : Stack(parent, id, props) {

    val vpc: Vpc = createVpc()
    val ecr: Repository = createEcr()

    private fun createVpc(): Vpc {
        return Vpc.Builder.create(this, "GitHubApplicationVpc")
            .maxAzs(3)
            .build()
    }

    private fun createEcr(): Repository {
        return Repository.Builder
            .create(this, "GitHubApplicationRepository")
            .repositoryName("github-application-repository")
            .removalPolicy(RemovalPolicy.DESTROY)
            .build()
    }
}