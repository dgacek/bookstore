# Bookstore
A simple book catalogue application written in Spring Boot using an embedded (file) H2 database, with a simple GUI in Angular.

## Build and run the application

### Requirements:
- Java 17

### Instructions:
Execute command `./gradlew bootJar` in the project directory.  
This will generate a .jar file and put it in the `./build/libs` directory.  
You can then run the application with the `java -jar <path-to-jar>` command.  
  
When run, the GUI is exposed at http://localhost:8080  
The application also exposes Swagger UI at http://localhost:8080/swagger-ui/index.html and the H2 console at http://localhost:8080/h2