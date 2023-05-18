def call(){
    println("JOB_NAME: ${env.JOB_NAME}") 
    def dockerImage = docker.build("julianmol007/${env.JOB_NAME}_nodejs_app:${env.BUILD_ID}")
}
