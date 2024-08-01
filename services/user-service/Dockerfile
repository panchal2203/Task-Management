# Stage 1: Build the application using Maven
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml file and the source code
COPY ./services/user-service/pom.xml pom.xml
COPY ./services/user-service/src src

# Run the Maven build
RUN mvn clean install -DskipTests

# Stage 2: Create the final image to run the application
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/user-service-1.0.0.jar user-service.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]

# Expose port 8080
EXPOSE 8080
