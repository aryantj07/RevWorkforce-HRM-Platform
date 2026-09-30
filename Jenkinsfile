pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out RevWorkforce repository'
            }
        }


        stage('Build') {
            steps {
                echo 'Building Spring Boot services'

                sh '''
                mvn clean package -DskipTests
                '''
            }
        }


        stage('Docker Images') {
            steps {
                echo 'Building Docker images'
            }
        }


        stage('Deploy Kubernetes') {
            steps {
                echo 'Deploying to Kubernetes'

                sh '''
                kubectl apply -f k8s/
                '''
            }
        }
    }
}