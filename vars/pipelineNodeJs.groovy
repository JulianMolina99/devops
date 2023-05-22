def call(parameters) {

    pipeline {
        agent any

        tools {
            nodejs 'NodeJS'
        }

        stages {
            stage('Checkout') {
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
                    script {
                        try {
                            analisysSonarNpm()
                        } catch (err) {
                            echo "The project did not pass the quality gate"
                        }
                    }
                }
            }

            // Etapas que solo se ejecutan en 'origin/develop'
            stage('Deploy and Analisys in Develop') {
                when {
                    beforeAgent true
                    expression { return env.GIT_BRANCH == 'origin/develop' }
                }
                stages {
                    stage('Build image Docker') {
                        steps {
                            script {
                                dockerBuild()
                            }
                        }
                    }

                    
                stage('Push Docker Image') {
                    steps {
                        script {
                            dockerPush()
                        }
                    }
                }

                    stage('Deploy App with Docker') {
                        steps {
                            script {
                                dockerDeploy()
                            }
                        }
                    }

                    stage('Analisys With OWASP ZAP') {
                        steps {
                            script {
                                owaspScan()
                            }
                        }
                    }
                
                }
            }
        }
    }
}
