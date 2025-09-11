pipeline {
    agent any

    environment {
        BACKEND_DIR = "BACKEND"
        DOCKERHUB_REPO = "pansura/demo"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'dev-cloud', url: 'https://github.com/vivekgshan/Log-Processing.git'
            }
        }

        stage('Build with Docker Compose') {
            steps {
                dir("${BACKEND_DIR}") {
                    script {
                        // Rebuild containers
                        sh 'docker compose -f docker-compose.yaml down || true'
                        sh 'docker compose -f docker-compose.yaml build'
                    }
                }
            }
        }

        stage('Push Images to DockerHub') {
            steps {
                dir("${BACKEND_DIR}") {
                    script {
                        withCredentials([usernamePassword(credentialsId: 'dockerhub',
                                                          usernameVariable: 'DOCKERHUB_USER',
                                                          passwordVariable: 'DOCKERHUB_PASS')]) {
                            sh '''
                                echo "$DOCKERHUB_PASS" | docker login -u "$DOCKERHUB_USER" --password-stdin

                                for service in logcollector logerrorpersistor logdebugpersistor logwarnpersistor loginfopersistor loganalyser; do
                                    echo "📦 Tagging and pushing $service..."
                                    docker tag backend-$service:latest ${DOCKERHUB_REPO}:$service
                                    docker push ${DOCKERHUB_REPO}:$service
                                done
                            '''
                        }
                    }
                }
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                dir("${BACKEND_DIR}") {
                    script {
                        // Run containers from compose
                        sh 'docker compose -f docker-compose.yaml up -d'
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'docker ps -a || true'
        }
    }
}
