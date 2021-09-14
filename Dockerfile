FROM maven:latest
COPY target/restaurant-backend.jar /home
ENTRYPOINT java -jar /home/restaurant-backend.jar
