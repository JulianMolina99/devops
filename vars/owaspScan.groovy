def call() {
    //sh 'docker run -u root --network=${env.JOB_NAME}_network_nodejs_app -v owasp_data:/zap/wrk/:rw -t owasp/zap2docker-stable zap-baseline.py -t http://nodejs_app:3000 -r report_baseInline.html'
    def owaspImage = docker.image('owasp/zap2docker-stable')
    owaspImage.withRun("-u root --network=${env.JOB_NAME}_network_nodejs_app -v owasp_data:/zap/wrk/:rw /usr/bin/tail -f /dev/null") { c ->
        sh "docker exec ${c.id} zap-baseline.py -t http://nodejs_app:3000 -r report_baseInline.html || true"

    }
}
