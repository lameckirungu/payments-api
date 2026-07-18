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
        
      }
  }
