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
                bat "docker build -t=devops1977/selenium ."
            }
        }

        stage('Push Image to Docker'){
            steps{
                bat "docker push devops1977/selenium"
            }
        }

    }


}