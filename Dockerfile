FROM alpine:latest
RUN apk add openjdk8
COPY target/ss-scrumptious-restaurant-0.0.1-SNAPSHOT.jar /home/restaurant-backend.jar
ENTRYPOINT java -jar /home/restaurant-backend.jar
