# GitHub Repo-App

This application exposes REST API which provides GitHub user repositories data

## Running the app

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