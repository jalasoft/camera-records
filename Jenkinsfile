pipeline {
    agent {
        docker {
            image 'maven17'
        }
    }
    stages {
        stage("build") {
            sh 'mvn clean package'
        }
    }
}