# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY ./target/user-service-1.0.0.jar user-service.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "/app/user-service.jar"]

# Expose port 8080
EXPOSE 8080
