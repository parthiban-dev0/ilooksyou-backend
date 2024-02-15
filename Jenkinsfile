pipeline {
    agent any
    environment{
        CLOUDSDK_CORE_PROJECT = 'looksyou-412005'
        VM_NAME = 'auth-service-instance'
        ZONE = 'asia-south1-b'
        GCLOUD_CREDS = credentials('gcloud-creds')
        SSH_PATH = 'C:\\Users\\Gspl\\.ssh\\google_compute_engine'
        DOCKER_LOCAL_IMAGE = 'auth-service:v4'
        DOCKER_REMOTE_IMAGE = 'parthiban0/auth-service:v4'
    }
    stages{
        stage('Docker Build And Push'){
            steps {
                bat ''' docker build -t %DOCKER_LOCAL_IMAGE% ./authservice/ '''
                bat ''' docker tag %DOCKER_LOCAL_IMAGE% %DOCKER_REMOTE_IMAGE% '''
            }
        }
        stage('Docker Push'){
            steps {
                bat ''' docker login -u parthiban0 -p Parthiban@12345 '''
                bat ''' docker push %DOCKER_REMOTE_IMAGE% '''
            }
        }
        stage('Deploy'){
            steps {
                bat '''gcloud auth activate-service-account --key-file=%GCLOUD_CREDS%'''
                bat '''gcloud compute ssh %VM_NAME% --command="docker pull %DOCKER_REMOTE_IMAGE% && docker ps -aq | xargs docker stop | xargs docker rm && docker run -d -p 8080:8080 %DOCKER_REMOTE_IMAGE%" --zone=%ZONE% --ssh-key-file=%SSH_PATH%'''
            }
        }
    }
}