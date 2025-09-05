pipeline {
    agent any

    stages {
        stage('Clone Code') {
            steps {
                echo "Cloning code from GitHub branch ramtest1..."
                git branch: 'ramtest1', url: 'https://github.com/vivekgshan/Log-Processing.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "Building Docker image using Dockerfile in BACKEND/logcreator..."
                script {
                    if (isUnix()) {
                        sh 'docker rm -f logcreator || true'
                        sh 'docker build -t logcreator:latest -f BACKEND/logcreator/Dockerfile .'
                    } else {
                        bat 'docker rm -f logcreator || exit 0'
                        bat 'docker build -t logcreator:latest -f BACKEND/logcreator/Dockerfile .'
                    }
                }
            }
        }

        stage('Start MySQL') {
            steps {
                echo "Starting MySQL container..."
                script {
                    if (isUnix()) {
                        sh '''
                        docker rm -f mysqldb || true
                        docker run -d --name mysqldb -e MYSQL_ROOT_PASSWORD=tiger -e MYSQL_DATABASE=logdb -p 3306:3306 mysql:8.0
                        '''
                    } else {
                        bat 'docker rm -f mysqldb || exit 0'
                        bat 'docker run -d --name mysqldb -e MYSQL_ROOT_PASSWORD=tiger -e MYSQL_DATABASE=logdb -p 3306:3306 mysql:8.0'
                    }
                }
            }
        }

        stage('Wait for MySQL') {
            steps {
                echo "⏳ Waiting 20s for MySQL to be ready..."
                script {
                    if (isUnix()) {
                        sh 'sleep 20'
                    } else {
                        powershell 'Start-Sleep -Seconds 20'
                    }
                }
            }
        }

        stage('Run App Container') {
            steps {
                echo "Running app container..."
                script {
                    if (isUnix()) {
                        sh '''
                        docker rm -f logcreator || true
                        docker run -d --name logcreator --link mysqldb:mysql -p 9096:9096 logcreator:latest
                        '''
                    } else {
                        bat 'docker rm -f logcreator || exit 0'
                        bat 'docker run -d --name logcreator --link mysqldb:mysql -p 9096:9096 logcreator:latest'
                    }
                }
            }
        }

        stage('Health Check (Logs Based)') {
            steps {
                echo "Checking container logs for Spring Boot startup message..."
                script {
                    if (isUnix()) {
                        sh '''
                        for i in {1..10}; do
                          if docker logs logcreator 2>&1 | grep -q "Started DemoApplication"; then
                            echo "✅ App started successfully"
                            exit 0
                          fi
                          echo "Waiting for app logs... ($i/10)"
                          sleep 5
                        done
                        echo "❌ App did not start in time"
                        exit 1
                        '''
                    } else {
                        powershell '''
                        $maxRetries = 10
                        $success = $false
                        for ($i=1; $i -le $maxRetries; $i++) {
                          $logs = docker logs logcreator 2>&1
                          if ($logs -match "Started DemoApplication") {
                            Write-Host "✅ App started successfully"
                            $success = $true
                            break
                          } else {
                            Write-Host "Waiting for app logs... ($i/$maxRetries)"
                            Start-Sleep -Seconds 5
                          }
                        }
                        if (-not $success) {
                          Write-Host "❌ App did not start in time"
                          exit 1
                        }
                        '''
                    }
                }
            }
        }

        stage('Test Logs Endpoint') {
            steps {
                echo "Calling /logs endpoint to verify app + DB..."
                script {
                    if (isUnix()) {
                        sh 'curl -s http://localhost:9096/logs || true'
                    } else {
                        powershell 'Invoke-WebRequest -UseBasicParsing http://localhost:9096/logs'
                    }
                }
            }
        }
    }

    post {
        always {
            echo "⚠️ Containers are left running for debugging:"
            echo "   docker ps -a"
            echo "   docker logs logcreator"
            echo "   docker logs mysqldb"
        }
    }
}
