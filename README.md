# GitHub Repo-App

This application exposes REST API which provides GitHub user repositories data

## Running the app locally

### Prerequisites:

- JDK17
- Gradle
- Docker
- GitHub token (Optional)

### How to run
First, you can provide environment variable `GITHUB_TOKEN` with your generated GitHub token.
You can also provide it as default in the resources/application.yml file. It's not necessary though.

To run the application locally you can run `run-local.sh` script.
The scrip build the application using `Gradle` and then run `docker-compose` which contains 2 instances 
of the application and the loadbalancer to spread request between instances.

The application is by default exposed on the port `8080`


## Deployment of the app on AWS manually

### Prerequisites:

- JDK17
- Gradle
- Docker
- AWS CLI & AWS CDK
- GitHub token

There is IaaC subproject `cdk` which utilizes AWS CDK.
To bootstrap the base infrastructure on a new AWS account you have to run:

`cdk bootstrap --profile <you-aws-profile>`

Then you should create CloudFormation templates:

`cdk synth --profile <you-aws-profile>`

Create the VPC and the ECR repository

`cdk deploy GitHubRepoBaseStack --profile <you-aws-profile>`

Then build and push the docker image to newly created ECR repository:

`aws ecr get-login-password --region us-east-1 --profile <you-aws-profile> | docker login --username AWS --password-stdin <ecr-url>`

`./gradlew clean build -x test`

`docker build -t github-repo-app:latest ./app`

`docker tag github-repo-app:latest <ecr-url>:latest`

`docker push <ecr-url>:latest`


Finally, deploy the application to the ECR:

`cdk deploy GitHubRepoApplicationStack --profile <you-aws-profile>`