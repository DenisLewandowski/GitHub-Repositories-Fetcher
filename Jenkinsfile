pipeline {

    agent any

    tools {
        gradle '8.5'
    }

    stages {

        stage('build') {
            steps {
                echo 'Building GitHub Repo Application...'
                sh 'gradle build -x test'
            }
        }

        stage('test') {
            steps {
                echo 'Testing GitHub Repo Application...'
            }
        }

        stage('deploy') {
            steps {
                echo 'Deploying GitHub Repo Application...'
            }
        }
    }
}