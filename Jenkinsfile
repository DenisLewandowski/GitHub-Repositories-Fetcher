pipeline {

    agent any

    tools {
        gradle '8.5'
    }

    environment {
        GITHUB_SECRET = credentials('github-token')
        GITHUB_TOKEN = "$GITHUB_SECRET_PSW"
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
                sh 'gradle test -p app'
            }
        }

        stage('deploy') {
            steps {
                echo 'Deploying GitHub Repo Application...'
            }
        }
    }
}