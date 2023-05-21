def call(Map parameters){
    git credentialsId: 'token_github', url: parameters.scmUrl
}
