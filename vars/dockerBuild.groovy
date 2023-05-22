def call(){
    def repoName = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1').toLowerCase()
    def dockerImage = docker.build("julianmol007/${repoName}:${env.BUILD_ID}")
}
