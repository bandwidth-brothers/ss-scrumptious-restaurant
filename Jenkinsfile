pipeline{
	agent any
	stages{
		stage('Checkout'){
			steps{
				checkout scm
				sh "chmod +x ./mvnw"
			}
		}
		stage('Analysis'){
			environment{
				SONARQUBE_TOKEN = credentials('sonarqube')
			}
			steps{
				sh "./mvnw clean verify sonar:sonar \\\n" +
					"  -Dsonar.host.url=http://sonarqube:9000 \\\n" +
					"  -Dsonar.login=${SONARQUBE_TOKEN}"
			}
		}
		stage('Build'){
			steps{
				sh './mvnw clean package'
			}
		}
		stage('Publish'){
			steps{
				withAWS(region: 'us-east-2', credentials: 'aws-creds'){
					s3Upload(bucket: 'ss-scrumptious-artifacts', file: 'target/ss-scrumptious-restaurant-0.0.1-SNAPSHOT.jar', path: 'restaurant-backend.jar')
				}
			}
		}
		stage('Deploy'){
			steps{
				sh "docker build -t ss-scrumptious-repo:restaurant-backend ."
				script{
					docker.withRegistry("https://419106922284.dkr.ecr.us-east-2.amazonaws.com/","ecr:us-east-2:aws-creds"){
						docker.image("ss-scrumptious-repo:restaurant-backend").push()
					}
				}
				sh "docker image rm ss-scrumptious-repo:restaurant-backend"
				sh "docker image rm 419106922284.dkr.ecr.us-east-2.amazonaws.com/ss-scrumptious-repo:backend"
			}
		}
	}
}
