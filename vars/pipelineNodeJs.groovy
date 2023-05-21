def call(parameters) {
    def repoBranch = env.GIT_BRANCH
    echo repoBranch

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
