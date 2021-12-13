pipeline {
    agent {
        docker {
            image 'maven17'
            args '-v mvn_vol:/root/.m2'
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