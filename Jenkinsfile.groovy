@Library('NodeJSApp_Library@feature') _

pipeline {
    agent any

    stages {
        script {
            buildStage().call()
        }
    }
}