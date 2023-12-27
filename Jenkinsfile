pipeline {

    agent any

    tools {
        gradle '8.5'
    }

    environment {
        GITHUB_TOKEN = credentials('github-token')
    }

    stages {
        stage('build') {
            steps {
                echo 'Building GitHub Repo Application...'
                sh 'gradle clean build -x test'
            }
        }

        stage('test') {
            steps {
                echo 'Testing GitHub Repo Application...'
// TODO            fix injecting GITHUB_TOKEN issue
//                 sh 'gradle test -p app'
            }
        }

        stage('deploy') {
            steps {
                echo 'Deploying GitHub Repo Application...'
                sh 'COMMIT_HASH=$(echo "$GIT_COMMIT" | cut -c 1-8)'
                //TODO sh 'cdk synth --profile <you-aws-profile>'
                //TODO sh 'cdk deploy GitHubRepoBaseStack --profile <you-aws-profile>'
                //TODO sh 'aws ecr get-login-password --region us-east-1 --profile <you-aws-profile> | docker login --username AWS --password-stdin <ecr-url>'
                //TODO sh ''./gradlew clean build -x test'
                //TODO sh 'docker build -t github-repo-app:latest ./app'
                //TODO sh 'docker tag github-repo-app:latest <ecr-url>:latest'
                //TODO sh 'docker push <ecr-url>:latest'
                //TODO sh 'cdk deploy GitHubRepoApplicationStack --profile <you-aws-profile>'
            }
        }
    }
}