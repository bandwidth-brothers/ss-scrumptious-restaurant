node{
	stage("checkout"){
		git branch:%env.BRANCH_NAME%, url:'https://github.com/bandwidth-brothers/ss-scrumptious-restaurant'
	}
	withCredentials([string(credentialsId: 'sonarqube', variable: 'SQC')]) {
		stage("verify"){
			bat 'mvn clean verify sonar:sonar -Dsonar.login=%SQC%'
		}
	}

	stage("build"){
		bat 'mvn clean package'
	}
	stage("publish"){
		withAWS(credentials:'s3-user',region:'us-west-2'){
			s3Upload(file:'target/ss-scrumptious-restaurant-0.0.1-SNAPSHOT.jar',bucket:'scrumptious-artifacts')
		}
	}
}
