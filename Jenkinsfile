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
                    bat 'docker build -t logcreator:latest -f BACKEND/logcreator/Dockerfile .'
                }
            }
        }
        stage('Run Container') {
            steps {
                echo "Running container from built image..."
                script {
                    bat 'docker rm -f logcreator || true'
                    bat 'docker run -d -p 8080:8080 --name logcreator logcreator:latest'
                }
            }
        }
    }
}
