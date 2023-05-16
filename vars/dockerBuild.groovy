def call(){
    def dockerImage = docker.build("app_nodejs:${env.BUILD_ID}")
}