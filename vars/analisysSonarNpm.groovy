def call (){
    def scannerTool = tool 'SonarQube Scanner'
    if(scannerTool){
        withSonarQubeEnv('SonarQube Local Server'){
            sh "${scannerTool}/bin/sonar-scanner \
            -Dsonar.projectKey='nodejs_project' \
            -Dsonar.projectName='nodejs_project' \
            -Dsonar.sources=src \
            -Dsonar.exclusions=src/__test__/** \
            -Dsonar.javascript.lcov.reportPaths=coverage/lcov.info"
        }
    } else{
        error 'SonarQube Scanner not found'
    }
}