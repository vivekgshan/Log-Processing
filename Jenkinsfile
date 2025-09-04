pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "vivekgshan"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Push Backend') {
            steps {
                script {
                    sh 'docker build -t $DOCKER_REGISTRY/log-processing-backend:latest ./BACKEND/logcreator'
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-cred', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                        sh 'docker push $DOCKER_REGISTRY/log-processing-backend:latest'
                    }
                }
            }
        }

        stage('Build & Push Frontend') {
            steps {
                script {
                    sh 'docker build -t $DOCKER_REGISTRY/log-processing-frontend:latest ./FRONTEND'
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-cred', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                        sh 'docker push $DOCKER_REGISTRY/log-processing-frontend:latest'
                    }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh 'docker-compose down || true'
                    sh 'docker-compose up -d --build'
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline finished"
        }
    }
}
