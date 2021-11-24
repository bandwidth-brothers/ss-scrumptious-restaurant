FROM maven:latest
COPY target/ss-scrumptious-restaurant-0.0.1-SNAPSHOT.jar /home/restaurant-service.jar
ENTRYPOINT java -jar /home/restaurant-service.jar
