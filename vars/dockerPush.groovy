def call(){
    def dockerImage = docker.image("nodejs_app:${env.BUILD_ID}")
    docker.withRegistry('https://registry.hub.docker.com', 'docker-login') {
        dockerImage.push("${env.BUILD_ID}")
    }
}