pipeline {
    agent any

    // tool versions must match the names you configured
    tools {
        jdk 'jdk-21'
        maven 'Maven-3.9'
      }

    environment {
        APP_NAME = 'payments-api'
        IMAGE_TAG = "${GIT_COMMIT[0..7]}"
        IMAGE_NAME = "localhost:5000/${APP_NAME}"
      }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "Building commit: ${GIT_COMMIT}"
                echo "Image will be tagged: ${IMAGE_TAG}"
              }
          }
        stage('Build') {
            steps {
                // -DskipTests because we run tests in a dedicated stage
                sh 'mvn clean package -DskipTests'
              }
              post {
                success {
                  // Archiving the JAR so as to download it from Jenkins UI
                  archiveArtifacts: 'target/*.jar', fingerprint: true
                }
              }
          }
        stage('Test')  {
            steps {
                sh 'mvn test'
              }
            post {
                // always runs whether tests pass or fail
                // publishes reports in any case
                always {
                    junit(
                      testResults: 'target/surefire-reports/**/*.xml',
                    )
                  }
              }
          }
        stage('Docker Build') {
          steps {
            script {
              echo "Building Docker image: ${IMAGE_NAME}:${IMAGE_TAG}"

              sh """
                docker build \
                -t ${IMAGE_NAME}:${IMAGE_TAG} \
                -t ${IMAGE_NAME}:latest \
              """
            }
          }
        }
        stage('Docker Push') {
          steps {
            sh """
              docker push ${IMAGE_NAME}:${IMAGE_TAG}
              docker push ${IMAGE_NAME}:latest
            """
          }
        post {
          always {
            sh "docker rmi ${IMAGE_NAME}:${IMAGE_TAG} || true"
          }
        }
      }
  }
  // post-pipeline actions
  post {
      success {
          echo "Image - ${IMAGE_NAME}:${IMAGE-TAG} built and pushed successfully"
        }
      failure {
          echo "Pipeline failed - check the logs above"
        }
      always {
          // clean the workspace after every run
          cleanWs() 
        }
    }
  }

