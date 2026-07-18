pipeline {
    agent any

    // tool versions must match the names you configured
    tools {
        jdk 'jdk-21'
        maven 'Maven-3.9'
      }

    environment {
        APP_NAME = 'payments-api'
      }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "Building commit: ${GIT_COMMIT"
              }
          }
        stage('Build') {
            steps {
                // -DskipTests because we run tests in a dedicated stage
                sh 'mvn clean package -DskipTests'
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
                      allowEmptyResults: false
                    )
                  }
              }
          }
      }
  }
  // post-pipeline actions
  post {
      success {
          echo "Pipeline succeeded - ${APP_NAME} built and tested"
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

