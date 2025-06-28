# Spring Boot REST API Dynata Test

## Technologies Used

- Java 21
- Spring Boot 3.3.3
- Spring Web (REST API)
- SpringDoc OpenAPI (Swagger) 2.4.0
- JUnit 5
- Mockito
- Maven
- Lombok


## Getting Started

### Prerequisites

- Java 21 or higher
- Maven

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application using Maven:

```
mvn spring-boot:run
```

The application will start on port 8080.


## API Documentation with Swagger

The API is documented using Swagger (OpenAPI 3). After starting the application, you can access the Swagger UI at:

```
http://localhost:8080/swagger-ui/index.html
```

This provides an interactive documentation where you can:
- View all available endpoints
- See detailed information about each endpoint (parameters, responses, etc.)
- Test the API directly from the browser

The OpenAPI specification is also available in JSON format at:

```
http://localhost:8080/v3/api-docs
```

And in YAML format at:

```
http://localhost:8080/v3/api-docs.yaml
```

## Testing

The project includes unit tests for both the service and controller layers. To run the tests:

```
mvn test
```

## Troubleshooting

### Native Access Warning

When running the application with recent Java versions, you might encounter a warning about a restricted method call from `org.apache.tomcat.jni.Library`. This is a security feature in Java.

To resolve this, add the following JVM option when running the application:
If you are using an IDE like IntelliJ IDEA, you can add this to the "VM options" field in your Run Configuration.
```
--add-opens java.base/java.lang=ALL-UNNAMED
