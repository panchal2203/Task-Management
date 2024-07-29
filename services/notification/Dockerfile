# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY ./target/notification-service-1.0.0.jar notification-service.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "/app/notification-service.jar"]

# Expose port 8082
EXPOSE 8082
