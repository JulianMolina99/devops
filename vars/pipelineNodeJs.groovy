def call() {
    node {
        stage('Checkout and Build app') {
            buildStage()
        }
    }
}