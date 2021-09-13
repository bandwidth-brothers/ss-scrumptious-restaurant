node{
	stage("sonarqube"){
		environment{
			scannerHome = tool 'local-sonar'
		}
		withSonarQubeEnv('sonarqube'){
			sh "${scannerHome}/bin/sonar-scanner"
		}
	}
	checkout scm
	withCredentials([string(credentialsId: 'sonarqube', variable: 'SQC')]) {
		stage("verify"){
			sh 'mvn clean verify sonar:sonar -Dsonar.login=%SQC% -Dsonar.host.url=http://67.168.136.215:9000 -Dsonar.jdbc.url=jdbc:h2:tcp://67.168.136.215:9092/sonar'
		}
	}

	stage("build"){
		sh 'mvn clean package'
	}
	stage("publish"){
		withAWS(credentials:'s3-user',region:'us-west-2'){
			s3Upload(file:'target/ss-scrumptious-restaurant-0.0.1-SNAPSHOT.jar',bucket:'scrumptious-artifacts')
		}
	}
}
