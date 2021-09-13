FROM maven:latest
RUN wget https://scrumptious-artifacts.s3.us-west-2.amazonaws.com/restaurant-backend.jar restaurant-backend.jar
ENTRYPOINT java -jar restaurant-backend.jar
