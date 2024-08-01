
**Steps to run the application using docker-compose:**

Prerequisites: Docker desktop, maven.

1. Clone/Download the repository.

2. Build all the microservices and run the app using docker-compose. This is done using ./start-all.sh script which creates the network and set the container dependencies based on the config mention in the docker-compose.yml and env. 
   This will build all the jar files and run all the services.
   ```
      ./start-all.sh
   ```
3. To test apis, import postman api collection from 
   ```
      ./postman_collection.json.
   ```

4. Register/login user with username, password and emailId. Access token will be returned in response, pass this token as bearer token in postman to access other apis.
5. Use http://localhost:5050/browser/ to manage user-database or task-database on pgAdmin. Refer configurations from docker-compose.yaml to create server.


 **FEATURES**

1. Functional

   - User Management: user can register, login, update, delete, fetch user details using rest apis. User authenticated using access-token.
   - Task Management: user can create, update, delete, fetch or mark task as complete. User can fetch all tasks by userid or username.
   - Notification: send communication when task created, updated or completed.

2. Non-Functional

   - There are three microservice container named user-service, task-service and notification-service.
   - Separate postgres database container for user-database and task-database.
   - Separate container for pgadmin and kafka.
   - Use ./.evn and ./docker-compose.yaml file to manage configurations.
   - Run ./start-all.sh to build services and run all containers on docker.
   - Run ./stop-all.sh to stop all containers on docker.
   - Tools & Frameworks - Spring Framework, Postgres, PgAdmin, Docker, Kafka, Postman, lombok, Intellij.
   - Grafana cloud extension on docker desktop is used to monitor servers.