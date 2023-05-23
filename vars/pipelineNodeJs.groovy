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
                        analisysSonarNpm()
                    }
                }
            }

             stage("Quality Gate") {
                steps {
                    timeout(time: 1, unit: 'HOURS') {
                        waitForQualityGate abortPipeline: false
                    }
                }
            }

            // Etapas que solo se ejecutan en origin/develop y origin/master
            stage('Deploy and Analisys in Develop') {
                when {
                    beforeAgent true
                    expression { return env.GIT_BRANCH == 'origin/develop' ||  env.GIT_BRANCH == 'origin/master'}
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
