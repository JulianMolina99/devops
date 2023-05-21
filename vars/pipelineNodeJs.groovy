def call(parameters) {

    echo "${env.GIT_BRANCH}"
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
                when {
                    expression {
                        return env.GIT_BRANCH == 'origin/develop'
                    }
                }
                steps {
                    script{
                        dockerBuild()
                    }
                }
            }

            /*  

            stage('Push Docker Image') {
                when {
                    expression {
                        return env.GIT_BRANCH == 'origin/develop'
                    }
                }
                steps {
                    script{
                        dockerPush()
                    }
                }
            }

            stage('Deploy App with Docker') {
                when {
                    expression {
                        return env.GIT_BRANCH == 'origin/develop'
                    }
                }
                steps {
                    script{
                        dockerDeploy()
                    }
                }
            }
            
            stage('Analisys With OWASP ZAP') {
                when {
                    expression {
                        return env.GIT_BRANCH == 'origin/develop'
                    }
                }
                steps {
                    script{
                        owaspScan()
                    }
                }
            }
            */
        }
        
    }

}
