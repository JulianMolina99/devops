def call(){
    def repoName = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1').toLowerCase()
    def dockerImage = docker.image("julianmol007/${repoName}:${env.BUILD_ID}")
    docker.withRegistry('https://registry.hub.docker.com', 'docker-login') {
        dockerImage.push("${env.BUILD_ID}")
    }
}

