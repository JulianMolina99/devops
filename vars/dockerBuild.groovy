def call(){
    def projectName = build.environment.get("GIT_URL").replaceAll('https://github.com/', '').replaceAll('.git', '')
    println(projectName)
    println("JOB_NAME: ${env.JOB_NAME}")
    def dockerImage = docker.build("julianmol007/${env.JOB_NAME}_nodejs_app:${env.BUILD_ID}")
}
