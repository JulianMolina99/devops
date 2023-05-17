def call(Map parameters) {

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

            stage('Build image Docker') {
                steps {
                    script{
                        dockerBuild()
                    }
                }
            }

            stage('Push Docker Image') {
                steps {
                    script{
                        dockerPush()
                    }
                }
            }
 
            stage('Deploy App with Docker') {
                steps {
                    script{
                        dockerDeploy()
                    }
                }
            }
            

            stage('Analisys With OWASP ZAP') {
                steps {
                    script{
                        owaspScan()
                    }
                }
            }

        
        }
    }
}
