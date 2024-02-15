pipeline {
    agent any
    stages{
        stage('Docker Build'){
            steps {
                sh 'docker build -t authservice:v4 ./authservice/'
            }
        }
    }
}