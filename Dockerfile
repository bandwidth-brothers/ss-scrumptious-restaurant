FROM maven:3.8.2-jdk-11
RUN mvn clean package
ENTRYPOINT java -jar target/ss-scrumptious-restaurant-SNAPSHOT-0.0.1
