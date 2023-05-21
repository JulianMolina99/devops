def call(parameters) {
    def repoBranch = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
    echo "${env.GIT_BRANCH}"

    switch(repoBranch) {            
			
         case 'master': 
            println("switch case from master"); 
            break; 
         case 'feature': 
            println("switch case from feature"); 
            break; 
         case 'develop': 
            println("switch case from develop"); 
            break; 
         default: 
            println("The value of branch is unknown"); 
            break; 
      }
    
}
