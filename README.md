This is a microservices-based Task Management application built with Spring Boot, Docker, and Kafka. 
The application consists of multiple microservices that handle user management, task management, and notifications.

**Steps for executing the application using docker-compose:**

Prerequisites: Docker desktop installed, maven installed,

1. Clone/Download the repository.

3. Build all the microservices and run the app using docker-compose. This is done using ./start-all.sh script which creates the network and set the container dependencies based on the config mention in the docker-compose.yml. 
   This will build all the jar files and run all the services.
   ```
      ./start-all.sh
   ```
4. To access apis, import postman api collection from [docs/Task Management.postman_collection.json]
 

 **FEATURES**

- There are three microservice container user-service, task-service and notification-service.
- Seperate postgres database container for user-database and task-database.
- Seperate container for pgadmin and kafka.
- use ./.evn and ./docker-compose.yaml file to change configurations.
- run ./start-all.sh to build services and run all containers on docker.
- run ./stop-all.sh to stop all containers on docker.

**TOOLS USED**

- **Spring Boot :** Back-end java framework to build microservices using Spring Rest Controller and Spring JPA.
- **Spring Security :** User authentication and authorization using jwt.
- **Postgres:** Stores user and task information.
- **Docker-Compose:** Easy way to bring up the application using containerization and behaves similarly in the production environment.
- **pgadmin:** Plateform to access postgres database.
- **kafka:** Distributed event store and stream-processing platform. 