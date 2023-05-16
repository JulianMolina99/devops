def call(Map parameters){
    if(parameters.branch == 'master'){
        echo 'hello from devops repo in master branch'
    } else if(parameters.branch == 'feature'){
        echo 'hello from devops repo in feature branch'
    }
}
