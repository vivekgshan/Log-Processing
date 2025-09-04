pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = "vivekgshan/log-processing"
        GIT_REPO = "https://github.com/vivekgshan/Log-Processing.git"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: "${GIT_REPO}"
            }
        }

        stage('Build & Push Docker Images') {
            steps {
                script {
                    def services = [
                        "log-generator",
                        "log-listener",
                        "log-collector",
                        "log-info-persistor",
                        "log-warn-persistor",
                        "log-debug-persistor",
                        "log-error-persistor",
                        "log-exporter-analyzer",
                        "ui",
                        "testing"
                    ]
                    for (s in services) {
                        sh """
                          echo "🚀 Building image for ${s}"
                          docker build -t ${DOCKER_REGISTRY}:${s} ${s}/
                          docker push ${DOCKER_REGISTRY}:${s}
                        """
                    }
                }
            }
        }

        stage('Deploy to EC2') {
            steps {
                sshagent (credentials: ['ec2-ssh-key']) {
                    sh '''
                        ssh -o StrictHostKeyChecking=no ec2-user@<EC2-IP> '
                          if [ ! -d "/home/ec2-user/Log-Processing" ]; then
                              git clone https://github.com/vivekgshan/Log-Processing.git
                          fi &&
                          cd /home/ec2-user/Log-Processing &&
                          git pull origin main &&
                          docker-compose down &&
                          docker-compose pull &&
                          docker-compose up -d
                        '
                    '''
                }
            }
        }
    }

    post {
        always {
            echo "✅ Pipeline finished successfully!"
        }
    }
}
