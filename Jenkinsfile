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
				withAWS(region: 'us-west-2', credentials: 's3-user'){
					s3Upload(bucket: 'scrumptious-artifacts', file: 'target/ss-scrumptious-restaurant-0.0.1-SNAPSHOT.jar', path: 'restaurant-backend.jar')
				}
			}
		}
		stage('Deploy'){
			steps{
				sh "docker build -t restaurant-backend:latest ."
				docker.withRegistry("419106922284.dkr.ecr.us-east-2.amazonaws.com","ecr:us-east-2:aws-creds"){
					docker.image("restaurant-backend:latest").push()
				}
			}
		}
	}
}
