# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file and the source code
COPY ./services/notification-service/pom.xml pom.xml
COPY ./services/notification-service/src src

# Run the Maven build
RUN mvn clean install -DskipTests

# Stage 2: Create the final image to run the application
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/notification-service-1.0.0.jar notification-service.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5007", "-jar", "/app/notification-service.jar"]

# Expose port 8082
EXPOSE 8082

EXPOSE 5007