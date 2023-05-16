def call(){
    def dockerImage = docker.image("julianmol007/nodejs_app:${env.BUILD_ID}")
    docker.withRegistry('https://registry.hub.docker.com', 'docker-login') {
        dockerImage.push("${env.BUILD_ID}")
    }
}
