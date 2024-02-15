pipeline {
    agent any
    stages{
        stage('Docker Build'){
            steps {
                bat 'docker build -t authservice:v4 ./authservice/'
            }
        }
    }
}