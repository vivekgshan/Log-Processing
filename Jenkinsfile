pipeline {
    agent any
    stages {
        stage('Clone Code') {
            steps {
                echo "Cloning code from GitHub dev branch..."
                git branch: 'ramtest1', url: 'https://github.com/vivekgshan/Log-Processing.git'
            }
        }
        stage('Build Docker Image') {
            steps {
                echo "Building Docker image using Dockerfile in BACKEND/logcreator..."
                script {
                    sh 'docker build -t logcreator:latest ./BACKEND/logcreator'
                }
            }
        }
        stage('Run Container') {
            steps {
                echo "Running container from built image..."
                script {
                    sh 'docker rm -f logcreator || true'
                    sh 'docker run -d -p 8080:8080 --name logcreator logcreator:latest'
                }
            }
        }
    }
}
