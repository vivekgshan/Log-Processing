pipeline {
    agent any

    stages {
        stage('Clone Code') {
            steps {
                echo "Cloning code from GitHub branch ramtest1..."
                git branch: 'ramtest1',
                    url: 'https://github.com/vivekgshan/Log-Processing.git',
                    
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
                        sh '''
                        docker rm -f logcreator || true
                        docker run -d -p 9090:9090 --name logcreator logcreator:latest
                        '''
                    } else {
                        bat 'docker rm -f logcreator || exit 0'
                        bat 'docker run -d -p 9090:9090 --name logcreator logcreator:latest'
                    }
                }
            }
        }

        stage('Health Check') {
            steps {
                echo "Checking if application is up on port 9090..."
                script {
                    if (isUnix()) {
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
                        powershell '''
                        $maxRetries = 5
                        for ($i=1; $i -le $maxRetries; $i++) {
                          try {
                            Invoke-WebRequest -UseBasicParsing http://localhost:9090 | Out-Null
                            Write-Host "✅ App is up!"
                            exit 0
                          } catch {
                            Write-Host "Waiting for app... ($i/$maxRetries)"
                            Start-Sleep -Seconds 5
                          }
                        }
                        Write-Host "❌ App did not start in time"
                        exit 1
                        '''
                    }
                }
            }
        }
    }
}
