pipeline{

  agent any

  environment
  {
    IMG_NAME = ${env.JOB_NAME}
    AWS_ID = credentials('AWS_ID')
    DB_ENDPOINT = credentials('DB_ENDPOINT')
    DB_USERNAME = credentials('DB_USERNAME')
    DB_PASSWORD = credentials('DB_PASSWORD')
  }

  tools
  {
            maven 'maven'
            jdk 'java'
  }

  stages
  {
      
      state("Stage")
      {
        steps
        {
          script{
            def files = findFiles(glob: '**/main/resources/application-product.properties')
            echo """name ${files[0].name}; path:  ${files[0].path}; directory: ${files[0].directory}; length: ${files[0].length}; modified:  ${files[0].lastModified}"""

            def readContent = readFile "${files[0].path}"
            writeFile file: "${files[0].path}", text: readContent+"\r\nspring.datasource.username:${DB_USERNAME}"+"\r\nspring.datasource.password:${DB_PASSWORD}"+"\r\nspring.datasource.url:${DB_ENDPOINT}"

            def str=readFile file: "${files[0].path}"
            echo str
          }
        }
      }

      stage("Package")
      {
            steps
            {
                sh 'mvn clean package'
            }
      }
      stage("Docker Build") {

          steps {
              echo "Docker Build...."
              withCredentials([aws(accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'jenkins_credentials', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY')]) {
                        sh "aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin ${AWS_ID}.dkr.ecr.us-east-2.amazonaws.com"
              }
              sh "docker build -t ${IMG_NAME} ."
               sh "docker tag ${IMG_NAME}:latest ${AWS_ID}.dkr.ecr.us-east-2.amazonaws.com/${IMG_NAME}:latest"
              echo "Docker Push..."
               sh "docker push ${AWS_ID}.dkr.ecr.us-east-2.amazonaws.com/${IMG_NAME}:latest"
          }
      }
    }
  post
  {
          always
          {
              sh 'mvn clean'
          }
  }

}
