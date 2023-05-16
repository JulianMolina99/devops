def call(){
    def dockerImage = docker.build("nodejs_app:${env.BUILD_ID}")
}