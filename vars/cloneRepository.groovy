def call(Map parameters){
    git branch:parameters.branch, credentialsId: 'token_github', url: parameters.scmUrl
}
