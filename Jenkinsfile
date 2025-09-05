pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'sumanthmurari'  
        FRONTEND_IMAGE = "${DOCKER_REGISTRY}/frontend:latest"
        BACKEND_IMAGE = "${DOCKER_REGISTRY}/backend:latest"
    }

    stages {
        stage('Build Frontend Image') {
            steps {
                script {
                    docker.build(FRONTEND_IMAGE, './frontend')
                }
            }
        }

        stage('Build Backend Image') {
            steps {
                script {
                    docker.build(BACKEND_IMAGE, './backend')
                }
            }
        }

        stage('Push Images') {
            steps {
                script {
                    docker.withRegistry("https://index.docker.io/v1/", 'dockerhub-credentials-id') {
                        docker.image(FRONTEND_IMAGE).push()
                        docker.image(BACKEND_IMAGE).push()
                    }
                }
            }
        }
    }
}

