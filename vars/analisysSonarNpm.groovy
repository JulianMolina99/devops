def call (){
    def scannerTool = tool 'SonarQube Scanner'
    def repoName = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1').toLowerCase()
    if(scannerTool){
        withSonarQubeEnv('SonarQube Local Server'){
            sh "${scannerTool}/bin/sonar-scanner \
            -Dsonar.projectKey='${repoName}' \
            -Dsonar.projectName='${repoName}' \
            -Dsonar.sources=src \
            -Dsonar.tests=src/__test__ \
            -Dsonar.exclusions=src/__test__/** \
            -Dsonar.testExecutionReportPaths=./test-report.xml \
            -Dsonar.javascript.lcov.reportPaths=coverage/lcov.info"

            def qg = waitForQualityGate()
            if (qg.status != 'OK') {
                error "Pipeline detenido debido a que los Quality Gates no han pasado: ${qg.status}"
            }
        }
    } else{
        error 'SonarQube Scanner not found'
    }
}


 
