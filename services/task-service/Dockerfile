# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file and the source code
COPY ./services/task-service/pom.xml pom.xml
COPY ./services/task-service/src src

# Run the Maven build
RUN mvn clean install -DskipTests

# Stage 2: Create the final image to run the application
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/task-service-1.0.0.jar task-service.jar

# Specify the command to run the application with remote debugging enabled
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "/app/task-service.jar"]

# Expose port 8080
EXPOSE 8081

# Expose the debug port
EXPOSE 5005
