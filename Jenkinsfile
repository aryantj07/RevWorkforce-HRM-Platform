pipeline {
    agent any

    options {
        timeout(time: 30, unit: 'MINUTES')
    }

    stages {
        stage('Build Core Backend Services') {
            steps {
                echo '=== Compiling Core Spring Boot microservices ==='
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

        stage('Build and Load Docker Images to K8s') {
            steps {
                echo '=== Building Docker images and loading into Kubernetes containerd ==='
                sh '''
                docker build -t revworkforce-user-service:latest -f services/user-service/Dockerfile services/user-service
                docker save revworkforce-user-service:latest | docker exec -i desktop-control-plane ctr --namespace k8s.io images import -

                docker build -t revworkforce-api-gateway:latest -f infrastructure/api-gateway/Dockerfile infrastructure/api-gateway
                docker save revworkforce-api-gateway:latest | docker exec -i desktop-control-plane ctr --namespace k8s.io images import -
                '''
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo '=== Applying Kubernetes manifests and rolling out ==='
                sh '''
                kubectl apply -f k8s/services/services.yaml -n revworkforce
                kubectl apply -f k8s/gateway/gateway.yaml -n revworkforce

                kubectl rollout restart deployment/user-service -n revworkforce
                kubectl rollout restart deployment/api-gateway -n revworkforce

                kubectl rollout status deployment/user-service -n revworkforce --timeout=90s
                kubectl rollout status deployment/api-gateway -n revworkforce --timeout=90s
                '''
            }
        }
    }

    post {
        success {
            echo 'RevWorkforce CI/CD Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline encountered an error. Check logs above.'
        }
    }
}