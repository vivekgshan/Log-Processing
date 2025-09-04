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
                    if (isUnix()) {
                        sh 'docker build -t logcreator:latest -f BACKEND/logcreator/Dockerfile .'
                    } else {
                        bat 'docker build -t logcreator:latest -f BACKEND/logcreator/Dockerfile .'
                    }
                }
            }
        }

        stage('Run Container') {
            steps {
                echo "Running container from built image..."
                script {
                    if (isUnix()) {
                        sh 'docker rm -f logcreator || true'
                        sh 'docker run -d -p 9090:8080 --name logcreator logcreator:latest'
                    } else {
                        bat 'docker rm -f logcreator || exit 0'
                        bat 'docker run -d -p 9090:8080 --name logcreator logcreator:latest'
                    }
                }
            }
        }

        stage('Health Check') {
            steps {
                echo "Checking if application is up on port 9090..."
                script {
                    if (isUnix()) {
                        // Retry 5 times, sleep 5s between tries
                        sh '''
                        for i in {1..5}; do
                          if curl -s http://localhost:9090 > /dev/null; then
                            echo "✅ App is up!"
                            exit 0
                          fi
                          echo "Waiting for app... ($i/5)"
                          sleep 5
                        done
                        echo "❌ App did not start in time"
                        exit 1
                        '''
                    } else {
                        // Windows PowerShell retry
                        bat '''
                        for /l %%x in (1, 1, 5) do (
                          curl -s http://localhost:9090 >NUL 2>&1
                          if not errorlevel 1 (
                            echo ✅ App is up!
                            exit /b 0
                          )
                          echo Waiting for app... (%%x/5)
                          timeout /t 5 >NUL
                        )
                        echo ❌ App did not start in time
                        exit /b 1
                        '''
                    }
                }
            }
        }
    }
}
