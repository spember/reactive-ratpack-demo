pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
	agent any

	stages {

		stage ('Initialize') {
			steps {
				echo "whoop"
			}
		}
		stage("Checkout source") {
            steps {
                checkout scm
            }
        }

        stage("Test") {
            environment {
                RABBITMQ_URL='amqp://localhost:5672'
                //JDBC_URL='jdbc:postgresql://localhost:5432/database_name'
                //JDBC_USERNAME='admin'
                //JDBC_PASSWORD=''
            }
            steps {
                script {
                    //docker.image('postgres:9.5').run("-p 5432:5432 --name db -e POSTGRES_USER=admin -e POSTGRES_DB=database_name")
                    docker.image('rabbitmq:3.6-alpine').run("-p 5672:5672 -p 5671:5671 -p 4369:4369 --hostname my-rabbit --name rabbit")
                }
                sh './gradlew --stacktrace test'
            }
        }

        stage("Build Jar") {
            steps {
                sh './gradlew --stacktrace project-name:shadowJar -x test '
            }
        }
	}
}