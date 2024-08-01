# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY ./services/task-service/target/task-service-1.0.0.jar task-service.jar

# Expose port 8081
EXPOSE 8081
EXPOSE 5005

# Specify the command to run the application
ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005", "-jar", "/app/task-service.jar"]


