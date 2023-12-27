# GitHub Repo-App

This application exposes REST API which provides GitHub user repositories data

## Running the app locally

### Prerequisites:

- JDK17
- Gradle
- Docker
- GitHub token

### How to run
First, you have to provide environment variable `GITHUB_TOKEN` with your generated GitHub token.
You can also provide it as default in the resources/application.yml file.

To run the application locally you can run `run-local.sh` script.
The scrip build the application using `Gradle` and then run `docker-compose` which contains 2 instances 
of the application and the loadbalancer to spread request between instances.

The application is by default exposed on the port `8080`


## Running the app on AWS

There is IaaC subproject `cdk` which utilizes AWS CDK.
To bootstrap the base infrastructure on a new AWS account you have to run:

`cdk bootstrap --profile <you-aws-profile>`

then you should synthesize running:

`cdk synth --profile <you-aws-profile>`

and finally 

`cdk deploy --profile <you-aws-profile>`

to create ECR repository and ECS cluster
