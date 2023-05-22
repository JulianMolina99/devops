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
                        try{
                            analisysSonarNpm()
                        } catch (err) {
                            echo "The project did not pass the quality gate"
                        }
                        
                    }
                }
            }

            stage('Build image Docker') {
                when{
                    beforeAgent true
                    expression{ return env.GIT_BRANCH == 'origin/develop'}
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
