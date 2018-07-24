# API Challenge

This application creates a REST API that allows all CRUD operations on a employee entity.

The employee entity has the following fields:
* UUID (generated automatically)
* E-mail
* Full name (first and last name)
* Birthday 
* List of hobbies

Users maybe updated as the user pleases, the only restriction being that two users cannot
have the same email address.

The solution leverages Spring Boot packages such as: `spring-boot-starter-web`, 
`spring-boot-starter-data-jpa`, `spring-boot-starter-security`, 
`spring-boot-starter-test`

Storage of data is done using a `mysql` Docker container. The database can be started using 
`docker-compose` command on the root of the project. `docker-compose -f docker/stack.yml up db`.

Once the database is up and running, the application can also be started. The build tool used 
is `maven`. First, run `mvn clean install` in the root of the project. This will insure all
dependencies have been downloaded and added to the classpath. To start the application run 
`mvn spring-boot:run`. The application will start on port `9100`. 

The root context of the API is `/employee`. Further endpoints and methods can be found on the 
Swagger UI `http://localhost:9100/swagger-ui.html`

Once the application is running you can test it using `curl` or `postman`.

Curl requests could be created based on Swagger documentation or the Postman collection 
included in this repo could be used as a starting point for testing manually.  


 
 
