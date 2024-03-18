pipeline{

    agent any

    stages{

        stage('Build Job'){
            steps{
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Build Docker Image'){
            steps{
                bat "docker build -t=devops1977/selenium:latest ."
            }
        }

        stage('Push Image to DockerHub'){
            environment{
                DOCKER_HUB = credentials('dockerhub_credentials')
            }
            steps{
            // there might be warning with this approch
            bat 'docker login -u %DOCKER_HUB_USR% -p %DOCKER_HUB_PSW%'
            bat 'docker push devops1977/selenium:latest'
            bat "docker tag devops1977/selenium:latest devops1977/selenium:${env.BUILD_NUMBER}"
            bat "docker push devops1977/selenium:${env.BUILD_NUMBER}"
            }
        }

    }
    post{
        always{
            bat "docker logout"
        }
    }
}