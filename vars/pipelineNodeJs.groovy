def call(Map parameters) {

    pipeline {
        agent any
        tools {
            nodejs 'NodeJS'
        }

        stages {
            stage ('Checkout') {
                steps {
                    cloneRepository()
                    echo 'parameters.scmUrl'
                    echo 'parameters.branch'
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
