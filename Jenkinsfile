pipeline{

  agent any

  environment
  {
    AWS_ID = credentials('AWS_ID')
    LOCATION = credentials("LOCATION")
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
      
    stage("Stage")
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
    stage("Docker Build") 
    {
      steps
      {
        sh "docker build -t ${env.JOB_NAME} ."
        script{
          docker.withRegistry("https://${AWS_ID}.dkr.ecr.${LOCATION}.amazonaws.com/","ecr:${LOCATION}:ecr_credentials"){
            docker.image("${env.JOB_NAME}").push()
          }
        }
        sh "docker system prune -fa"
      }
    }
  }
}

