pipelineJob('pipeline nodejs_app') {
    definition {
        cpsScm {
            scm {
                git {
                    branch('feature')
                    remote {
                        url('https://github.com/JulianMolina99/nodejs_application.git')
                        credentials('token_github')
                    }
                }
            }
            scriptPath('Jenkinsfile')
        }
    }
    properties {
        pipelineTriggers {
            triggers {
                cron {
                    spec('0 */2 * * *')
                }
            }
        }
    }
}
