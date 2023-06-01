def call() {
    def owaspImage = docker.image('owasp/zap2docker-stable')
    owaspImage.withRun("-u root --network=${env.JOB_NAME}_network_nodejs_app -v owasp_data:/zap/wrk/:rw owasp/zap2docker-stable /usr/bin/tail -f /dev/null") { c ->
        sh "docker exec ${c.id} zap-baseline.py -t http://test_mario:3001 -r report_baseInline.html || true"
        sh "docker exec ${c.id} zap-full-scan.py -t http://test_mario:3001 -r report_fullScan.html || true"
        sh "docker cp ${c.id}:/zap/wrk/report_baseInline.html report_baseInline.html"
        sh "docker cp report_baseInline.html jenkins:/var/jenkins_home/workspace/${env.JOB_NAME}/"
        sh "docker cp ${c.id}:/zap/wrk/report_fullScan.html report_fullScan.html"
        sh "docker cp report_fullScan.html jenkins:/var/jenkins_home/workspace/${env.JOB_NAME}/"
    }
}
/*
def call() {
    def owaspImage = docker.image('owasp/zap2docker-stable')
    owaspImage.withRun("-u root --network=mariored -v owasp_data:/zap/wrk/:rw owasp/zap2docker-stable /usr/bin/tail -f /dev/null") { c ->
        sh "docker exec ${c.id} zap-baseline.py -t http://marioapp:3000 -r report_baseInline.html || true"
        sh "docker exec ${c.id} zap-full-scan.py -t http://marioapp:3000 -r report_fullScan.html || true"
        sh "docker cp ${c.id}:/zap/wrk/report_baseInline.html report_baseInline.html"
        sh "docker cp report_baseInline.html jenkins:/var/jenkins_home/workspace/${env.JOB_NAME}/"
        sh "docker cp ${c.id}:/zap/wrk/report_fullScan.html report_fullScan.html"
        sh "docker cp report_fullScan.html jenkins:/var/jenkins_home/workspace/${env.JOB_NAME}/"
    }
}
*/

