# Scrumptious Restaurant Service

Microservice to handle Restaurant Owner and Admin accounts.

Restaurant Microservice also allows control to:
- Restaurants
- Menu Items
- Admins

## Documentation

Microservice has Swagger UI enabled and allows visualization and interaction with the API’s resources. The visual documentation is available by going to `http://localhost:8080/restaurant/swagger-ui/`

## Requirements

The service runs on port 8010 and requires a local instance of MySQL running on port 3306 with a database schema named `scrumptious`.

The service may also be ran through the routing service, in which case the host url will be `http://localhost:8080/restaurant`. Note that the routing service must be running as well. ([see Api-Gateway](https://github.com/bandwidth-brothers/ss-scrumptious-apigateway/tree/main)).

The Restaurant microservice is a client for the service discovery server Eureka and should be used in conjunction with the Routing service. ([see Eureka](https://github.com/bandwidth-brothers/ss-scrumptious-eureka)).

## Setup

Unit tests for the service can be ran with `mvn clean package`.
After the above is accomplished, the service can be started with `mvn spring-boot:run` or `mvn spring-boot:start` to fork a new process.
