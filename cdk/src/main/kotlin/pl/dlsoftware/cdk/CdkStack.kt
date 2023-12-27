package pl.dlsoftware.cdk

import software.amazon.awscdk.Stack
import software.amazon.awscdk.StackProps
import software.amazon.awscdk.services.apigateway.ApiDefinition
import software.amazon.awscdk.services.apigateway.SpecRestApi
import software.amazon.awscdk.services.apigateway.StageOptions
import software.amazon.awscdk.services.ec2.Vpc
import software.amazon.awscdk.services.ecr.Repository
import software.amazon.awscdk.services.ecs.Cluster
import software.amazon.awscdk.services.ecs.ContainerImage
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck
import software.constructs.Construct

class CdkStack constructor(
    parent: Construct,
    id: String?,
    props: StackProps? = null) : Stack(parent, id, props) {

    init {
        val vpc = Vpc.Builder.create(this, "GitHubApplicationVpc")
            .maxAzs(3)
            .build()

        val ecrRepository = Repository.Builder
            .create(this, "GitHubApplicationRepository")
            .repositoryName("github-application-repository")
            .build()

        val cluster = Cluster.Builder.create(this, "GitHubApplicationCluster")
            .vpc(vpc)
            .build()

        val loadBalancedEcsApp = ApplicationLoadBalancedFargateService.Builder
            .create(this, "GitHubApplicationEcs")
            .cluster(cluster)
            .cpu(256)
            .memoryLimitMiB(512)
            .desiredCount(2)
            .taskImageOptions(ApplicationLoadBalancedTaskImageOptions.builder()
                .image(ContainerImage.fromEcrRepository(ecrRepository))
                .containerPort(8080)
                .build())
            .publicLoadBalancer(true)
            .build()

        loadBalancedEcsApp.targetGroup.configureHealthCheck(HealthCheck.builder()
            .path("/actuator/health")
            .build())

        SpecRestApi.Builder.create(this, "SpecRestApi")
            .restApiName("GitHubApplicationRestApi")
            .deployOptions(StageOptions.builder()
                .variables(mutableMapOf("albUri" to loadBalancedEcsApp.loadBalancer.loadBalancerDnsName))
                .build())
            .apiDefinition(ApiDefinition.fromAsset("./../app/src/main/resources/static/api-docs.yaml"))
            .deploy(true)
            .build()
    }
}