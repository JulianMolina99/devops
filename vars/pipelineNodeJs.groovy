def call() {
    node {
        stage('Checkout and build app') {
            def nodeHome = tool name: 'NodeJS', type: 'nodejs'
            env.PATH = "${nodeHome}/bin:${env.PATH}"
            buildStages()
        }

        
    }
}