def call(){
    
    withEnv(["JOB_NAME=${env.JOB_NAME}","BUILD_ID=${env.BUILD_ID}"]) {
        sh 'docker-compose up -d'
    }
    
}
