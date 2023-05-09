@Library('NodeJSApp_Library@feature') _

pipeline {
    agent any

    stages {
        buildStage().call()
    }
}