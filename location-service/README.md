# Preparing
## Create docker image for posgres
This is a one time step.
To create docker image execute:
> cd docker-db\
> create-db-image.cmd
# Build project
## Build requirements
1. Maven
2. JAVA SDK 11 or greater
3. Docker
## Build steps
To build project and create docker image execute:
> build-docker.cmd

# Tests
After build:
1. Test results can be found at location-service/target/surefire-reports
2. Code coverage results can be found at location-service/target/site/jacoco/index.html

# Run project under Docker 
To run application execute:
>docker-start.cmd

To stop project execute:
>docker-stop.cmd
# Access to API
[Swagger schema](doc/swagger.yaml)

# Further steps
[Further steps](doc/Further%20steps.MD)