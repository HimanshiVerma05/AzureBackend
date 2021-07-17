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
    

    stages {
        stage('gitCheckout'){
             steps {
                // Get some code from a GitHub repository
               
               git credentialsId: 'github', url:'https://github.com/HimanshiVerma05/AzureBackend.git'
             }
        }
        stage('build && SonarQube analysis') {
            steps {
                withSonarQubeEnv('Test_Sonar') {
                   
                   
                        bat 'mvn clean install sonar:sonar -Dsonar.projectKey=sonar-himanshiverma -Dsonar.projectName=sonar-himanshiverma'
                    
                }
            }
        }
  //      stage("Quality Gate") {
    //        steps {
      //         timeout(time: 1, unit: 'HOURS'){
                    // Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    // true = set pipeline to UNSTABLE, false = don't
        //            waitForQualityGate abortPipeline: true
          //      }
            //}
        //}
         stage('Docker Image') {
             steps{
                 echo "Docker Image step "
                // bat "mvn clean install"
                 bat "docker build -t i_${username}_master --no-cache -f Dockerfile ."
             }
         }
         stage('Move Docker Image to Hub') {
             steps{
                 
                 bat "docker tag i_${username}_master ${registry}:${BUILD_NUMBER}"
                 bat "docker tag i_${username}_master ${registry}:latest"
                 
               
                 withDockerRegistry([credentialsId:'DockerHub' , url:""]){
                 
                 
                 bat "docker push ${registry}:${BUILD_NUMBER}"
                 bat "docker push ${registry}:latest"
                 }
             }
         }
         stage('Run the application container'){
             steps{
             //   bat "docker container stop c_${username}_master"
              //  bat "docker container rm c_${username}_master"
                bat "docker run -p 7100:8080  -d --name c_${username}_master ${registry}:latest"
             }
         }
       // stage('Build') {
        //    steps {
                
              // git credentialsId: 'github', url: 'https://github.com/HimanshiVerma05/AzureBackend.git'

                // Run Maven on a Unix agent.
          //      bat "mvn clean install"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
          //  }

          //  post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
           //     success {
             //       junit '**/target/surefire-reports/TEST-*.xml'
               //     archiveArtifacts 'target/*.jar'
                //}
            //}
       // }
    }
}
