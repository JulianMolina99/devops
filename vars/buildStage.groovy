def buildStage() {
    return {
        stage('Checkout') {
            steps {
                git credentialsId: 'token_github', url: 'https://github.com/JulianMolina99/nodejs_application.git'
            }
        }

        stage('Build app NodeJS') {
            steps {
                sh 'npm install'
                sh 'npm run build'
            }
        }

        stage('Build artifact') {
            steps {
                sh 'tar -czvf nodejs_app.tar.gz dist'
                archiveArtifacts artifacts: 'nodejs_app.tar.gz', fingerprint: true
            }
        }
    }
}