def call(parameters) {

    pipeline {
        agent any
        tools {
            nodejs 'NodeJS'
        }

        stages {
            stage ('Checkout') {
                steps {
                    cloneRepository(parameters)
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

            stage("Quality Gate") {
                steps {
                    qualityGate()
                }
            }
        }
    }
}
