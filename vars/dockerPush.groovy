def call(){
    def repoName = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
    def dockerImage = docker.image("julianmol007/${repoName}_nodejs_app:${env.BUILD_ID}")
    docker.withRegistry('https://registry.hub.docker.com', 'docker-login') {
        dockerImage.push("${env.BUILD_ID}")
    }
}

