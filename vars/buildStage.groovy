def call() {
    return {
        git branch: 'feature', credentialsId: 'token_github', url: 'https://github.com/JulianMolina99/nodejs_application.git'
        sh 'npm install'
        sh 'npm run build'
    }
}

