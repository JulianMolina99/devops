def call(){
    def repoName = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
    println(${repoName})
    println("JOB_NAME: ${env.JOB_NAME}")
    def dockerImage = docker.build("julianmol007/${env.JOB_NAME}_nodejs_app:${env.BUILD_ID}")
}
