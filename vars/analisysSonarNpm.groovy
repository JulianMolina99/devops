def call (){
    def scannerTool = tool 'SonarQube Scanner'
    if(scannerTool){
        withSonarQubeEnv('SonarQube Local Server'){
            sh "${scannerTool}/bin/sonar-scanner \
            -Dsonar.projectKey='nodejs_project' \
            -Dsonar.projectName='nodejs_project' \
            -Dsonar.sources=src \
            -Dsonar.tests=src/__test__ \
            -Dsonar.exclusions=src/__test__/** \
            -Dsonar.testExecutionReportPaths=./test-report.xml \
            -Dsonar.javascript.lcov.reportPaths=coverage/lcov.info \
            -Dsonar.qualitygate.wait=true \
            -Dsonar.qualitygate.timeout=300 \
            -Dsonar.sourceEncoding='UTF-8'"
        }
    } else{
        error 'SonarQube Scanner not found'
    }
}


 
