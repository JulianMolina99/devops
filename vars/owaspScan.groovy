def call(){
/*
    def myImage = docker.image('owasp/zap2docker-stable')
   
    myImage.withRun("-u root --network=${env.JOB_NAME}_network_nodejs_app -v owasp_data:/zap/wrk/:rw") { c ->
        sh "docker exec ${c.id} zap-baseline.py -t http://nodejs_app:3000 -r report_baseInline.html"
    }

    myImage.withRun("-u root --network=${env.JOB_NAME}_network_nodejs_app -v owasp_data:/zap/wrk/:rw") { c ->
        sh "docker exec ${c.id} zap-full-scan.py -t http://nodejs_app:3000 -r report_fullScan.html"
    }
    */

    sh 'docker run -u root --network=reto_1_network_nodejs_app -v owasp_data:/zap/wrk/:rw -t owasp/zap2docker-stable zap-baseline.py -t http://nodejs_app:3000 -r report_baseInline.html'
}
