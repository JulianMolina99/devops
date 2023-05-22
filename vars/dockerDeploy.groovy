def call(){
    def repoName = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1').toLowerCase()
    withEnv(["repoName=${repoName}","BUILD_ID=${env.BUILD_ID}"]) {
        sh 'docker-compose up -d'
    }
    
}
