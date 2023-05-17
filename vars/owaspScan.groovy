def call(){
    def myImage = docker.image('owasp/zap2docker-stable')
    withEnv(["BUILD_ID=${env.BUILD_ID}"]) {
        myImage.withRun('-u root --network=network_nodejs_app_${BUILD_ID} -v owasp_data:/zap/wrk/:rw') { c ->
        sh "docker exec ${c.id} zap-baseline.py -t http://nodejs_app:3000 -r report_baseInline.html"
    }
    }
}