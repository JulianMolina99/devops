def call(){
 def dockerImage = docker.build("julianmol007/nodejs_app:${env.BUILD_ID}")
}
