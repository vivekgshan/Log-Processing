# Start from a lightweight OpenJDK image
FROM openjdk:17-jdk-slim

# Copy the jar file from the host into the Docker image
COPY target/myapp-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]
