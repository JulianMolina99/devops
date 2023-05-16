def call(){
    withEnv(["BUILD_ID=${env.BUILD_ID}"]) {
        sh 'docker-compose up -d'
    }
}