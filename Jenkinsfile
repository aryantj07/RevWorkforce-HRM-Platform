pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Checking out RevWorkforce repository'
                checkout scm
            }
        }


        stage('Build Backend Services') {
            steps {
                echo 'Building Spring Boot services'

                sh '''
                cd infrastructure/config-server
                mvn clean package -DskipTests

                cd ../eureka-server
                mvn clean package -DskipTests

                cd ../api-gateway
                mvn clean package -DskipTests

                cd ../../services/user-service
                mvn clean package -DskipTests
                '''
            }
        }


        stage('Docker Build') {
            steps {
                echo 'Docker image build stage'
            }
        }


        stage('Kubernetes Deploy') {
            steps {
                echo 'Deploying Kubernetes manifests'

                sh '''
                kubectl apply -f k8s/
                '''
            }
        }
    }
}