def call(){
    withEnv(["COMPOSE_PROJECT_NAME=", "BUILD_ID=${env.BUILD_ID}"]) {
        sh 'docker-compose up -d'
    }
}