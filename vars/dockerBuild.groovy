def call(){
    def repoName = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
    echo repoName
    def dockerImage = docker.build("julianmol007/${repoName}_nodejs_app:${env.BUILD_ID}")
}
