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
                        sh 'docker build -t logcreator:latest -f BACKEND/logcreator/Dockerfile .'
                    } else {
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
                        docker run -d --name mysqldb \
                          -e MYSQL_ROOT_PASSWORD=tiger \
                          -e MYSQL_DATABASE=logdb \
                          -p 3306:3306 mysql:8
                        '''
                    } else {
                        bat 'docker rm -f mysqldb || exit 0'
                        bat 'docker run -d --name mysqldb -e MYSQL_ROOT_PASSWORD=tiger -e MYSQL_DATABASE=logdb -p 3306:3306 mysql:8'
                    }
                }
            }
        }

        stage('Run App Container') {
            steps {
                echo "Running logcreator container linked with MySQL..."
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

        stage('Health Check') {
            steps {
                echo "Checking if application is up on port 9096..."
                script {
                    if (isUnix()) {
                        sh '''
                        for i in {1..10}; do
                          if curl -s http://localhost:9096 > /dev/null; then
                            echo "✅ App is up!"
                            exit 0
                          fi
                          echo "Waiting for app... ($i/10)"
                          sleep 10
                        done
                        echo "❌ App did not start in time"
                        exit 1
                        '''
                    } else {
                        powershell '''
                        $maxRetries = 10
                        $success = $false
                        for ($i=1; $i -le $maxRetries; $i++) {
                          try {
                            Invoke-WebRequest -UseBasicParsing http://localhost:9096 | Out-Null
                            Write-Host "✅ App is up!"
                            $success = $true
                            break
                          } catch {
                            Write-Host "Waiting for app... ($i/$maxRetries)"
                            Start-Sleep -Seconds 10
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
    }

    post {
        always {
            echo "Cleaning up containers..."
            script {
                if (isUnix()) {
                    sh 'docker rm -f logcreator || true'
                    sh 'docker rm -f mysqldb || true'
                } else {
                    bat 'docker rm -f logcreator || exit 0'
                    bat 'docker rm -f mysqldb || exit 0'
                }
            }
        }
    }
}
