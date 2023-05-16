def call (){
    sh 'tar -czvf nodejs_app.tar.gz dist'
    archiveArtifacts artifacts: 'nodejs_app.tar.gz', fingerprint: true
}