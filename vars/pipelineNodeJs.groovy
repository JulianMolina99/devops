def call() {

    pipeline {
        agent any
        tools {
            nodejs 'NodeJS'
        }

        stages {
            stage ('Checkout') {
                steps {
                    cloneRepository()
                }
            }
            
            stage('Build app') {
                steps {
                    buildNpm()
                }
            }
            
            stage('Test app') {
                steps {
                    testNpm()
                }
            }
            
            stage('Build artifact') {
                steps {
                    buildArtifactNpm()
                }
            }
            
            stage('Analisys with sonar') {
                steps {
                    script{
                        analisysSonarNpm()
                    }
                }
            }
        }
    }
}
