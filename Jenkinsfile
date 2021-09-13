node{
	checkout scm
	withCredentials([string(credentialsId: 'sonarqube', variable: 'SQC')]) {
		stage("verify"){
			sh 'mvn clean verify sonar:sonar -Dsonar.login=%SQC%'
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
