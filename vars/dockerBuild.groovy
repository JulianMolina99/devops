def call(){
    println("JOB_NAME: ${env.JOB_NAME}") 
    def dockerImage = docker.build("julianmol007/nodejs_app:${env.BUILD_ID}")
}
