pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "mudam5"   // DockerHub username
        PROJECT_NAME = "log-processing"
        COMPOSE_FILE = "docker-compose.yml"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'dev',
                    url: 'https://github.com/vivekgshan/Log-Processing.git'
            }
        }

        stage('Build & Push Images') {
            steps {
                script {
                    // Get Git commit short hash for tagging
                    COMMIT_ID = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
                    DOCKER_TAG = "${COMMIT_ID}"

                    // Login to DockerHub
                    withCredentials([usernamePassword(credentialsId: 'docker', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'

                        echo "Building and pushing all microservice images using docker-compose..."
                        sh """
                            docker compose -f ${COMPOSE_FILE} build \
                                --build-arg COMMIT_ID=${DOCKER_TAG}

                            docker compose -f ${COMPOSE_FILE} push
                        """
                    }
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                script {
                    echo "Deploying all microservices using docker-compose..."
                    sh """
                        docker compose -f ${COMPOSE_FILE} down || true
                        docker compose -f ${COMPOSE_FILE} pull
                        docker compose -f ${COMPOSE_FILE} up -d
                    """
                }
            }
        }
    }

    post {
        success {
            echo "✅ Deployment successful! All microservices are running with docker-compose."
        }
        failure {
            echo "❌ Deployment failed. Check Jenkins logs."
        }
    }
}
