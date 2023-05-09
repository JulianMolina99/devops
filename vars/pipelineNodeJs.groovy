def call() {
    tools {
        nodejs 'NodeJS'
    }
    node {
        stage('Checkout and Build app') {
            buildStage()
        }
    }
}