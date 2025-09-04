pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "log-processing"
        DOCKER_TAG = "latest"
        DOCKER_REGISTRY = "vivekgshan"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'LogGeneratorDevOps-Pipeline',
                    url: 'https://github.com/vivekgshan/Log-Processing.git'
            }
        }

        stage('Build & Push Docker Image') {
            steps {
                script {
                    sh 'docker build -t $DOCKER_REGISTRY/$DOCKER_IMAGE:$DOCKER_TAG .'
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-cred', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                        sh 'docker push $DOCKER_REGISTRY/$DOCKER_IMAGE:$DOCKER_TAG'
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
