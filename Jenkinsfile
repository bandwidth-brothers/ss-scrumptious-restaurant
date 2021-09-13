pipeline{
	agent any
	stages{
		checkout scm
		stage('Analysis'){
			environment{
				SONARQUBE_TOKEN = credentials('sonarqube')
			}
			steps{
				sh "./mvnw sonar:sonar \\\n" +
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
	}
}
