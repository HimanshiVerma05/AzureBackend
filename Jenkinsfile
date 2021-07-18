pipeline {
    agent any

     environment{
         scannerHome = tool name:'SonarQubeScanner'
        registry = 'himanshiverma05/testjenkins'
		username='himanshiverma'
		
    }
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }
    
triggers {
      pollSCM('H/2 * * * *') 
    }         
    
    options{
        timestamps()
        timeout(time: 1, unit: 'HOURS')
        skipDefaultCheckout()
        buildDiscarder(logRotator(
            numToKeepStr: '3',
            daysToKeepStr: '30'
            ))
    }
    
    stages {
        stage('gitCheckout'){
             steps {
                // Get some code from a GitHub repository
               echo 'Checkout code'
                git poll:true,credentialsId: 'github',url:'https://github.com/HimanshiVerma05/AzureBackend.git'
              
             }
        }
      //  stage('build && SonarQube analysis') {
       //     steps {
        //        withSonarQubeEnv('Test_Sonar') {
                   
                   
          //              bat 'mvn clean install sonar:sonar -Dsonar.projectKey=sonar-himanshiverma -Dsonar.projectName=sonar-himanshiverma'
                    
            //    }
            //}
        //}
		
		 stage('Code Build'){
            steps{
                echo 'doing maven build '
                bat 'mvn clean install'
            }
        }
        
        stage('Unit Testing'){
            steps{
                echo 'doing unit testing'
                bat 'mvn test'
            }
        }
		
		 stage('SonarQube analysis'){
            steps{
                echo 'Sonar Analysis.'
                
                
                withSonarQubeEnv('Test_Sonar') {
					bat "${scannerHome}/bin/sonar-scanner \
					-Dsonar.projectKey=sonar-himanshiverma \
					-Dsonar.projectName=sonar-himanshiverma \
					-Dsonar.host.url=http://localhost:9000 \
					-Dsonar.java.binaries=target/classes"
                }
            }
        }
		
		
  
         stage('Docker Image build') {
             steps{
                 echo "Docker Image step "
               
                 bat "docker build -t i-${username}-master --no-cache -f Dockerfile ."
             }
         }
         stage('Move Docker Image to docker Hub') {
             steps{
                 
                 bat "docker tag i-${username}-master ${registry}:${BUILD_NUMBER}"
                 bat "docker tag i-${username}-master ${registry}:latest"
                 
               
                 withDockerRegistry([credentialsId:'DockerHub' , url:""]){
                 
                 
                 bat "docker push ${registry}:${BUILD_NUMBER}"
                 bat "docker push ${registry}:latest"
                 }
             }
         }
         stage('Run the application container'){
             steps{
			 
			 
			  try{
                        
                        bat "docker stop c-${username}-master"
                        
                        bat "docker container rm c-${username}-master"
                    }catch(Exception e){
                         //first time execution of command leads to exception 
                    }finally{
					
                        //start a new container
                        bat "docker run --name c-${username}-master -d -p 7100:8080 ${registry}:latest"
                    }
			 
			 
            
                bat "docker run -p 7100:8080  -d --name c-${username}-master ${registry}:latest"
             }
         }

    }
}

