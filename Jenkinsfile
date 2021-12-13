pipeline {
    agent {
        docker {
            image 'maven17'
        }
    }
    stages {
        stage("build") {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}