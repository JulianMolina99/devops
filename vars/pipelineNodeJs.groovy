def call() {
    node {
        stage('Checkout and build app') {
            def nodeHome = tool name: 'NodeJS', type: 'nodejs'
            env.PATH = "${nodeHome}/bin:${env.PATH}"
            buildStage()
        } 
        stage('Test and Analisys with Sonar') {
            analisysStage()
        } 
    }
}