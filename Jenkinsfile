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
                    bat 'docker run -d -p 9090:8080 --name logcreator logcreator:latest'
                }
            }
        }
        stage('Health Check') {
            steps {
                echo "Checking if application is up on port 9090..."
                script {
                    // Wait a few seconds for app startup
                    bat 'timeout /t 10'
                    
                    // Try to access the app (Windows: curl comes with Git Bash or Windows 10+)
                    bat 'curl -f http://localhost:9090 || (echo "App is not responding" && exit 1)'
                }
            }
        }
    }
}
