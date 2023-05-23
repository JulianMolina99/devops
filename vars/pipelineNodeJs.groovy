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

            stage("Quality Gate"){
                timeout(time: 1, unit: 'HOURS') {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
                }
            }

            // Etapas que solo se ejecutan en 'origin/develop'
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
