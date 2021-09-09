FROM maven:latest
COPY target/ss-scrumptious-restaurant-0.0.1-SNAPSHOT.jar /home
ENTRYPOINT java -jar /home/ss-scrumptious-restaurant-0.0.1-SNAPSHOT.jar
