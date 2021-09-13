FROM maven:latest
COPY restaurant-backend.jar /home
ENTRYPOINT java -jar /home/restaurant-backend.jar
