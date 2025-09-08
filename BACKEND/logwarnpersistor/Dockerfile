# Use a lightweight JDK base image
FROM eclipse-temurin:17-jdk-alpine
# Set working directory
WORKDIR /app
# Copy the built JAR file into the container
COPY target/*.jar app.jar
# Expose the application port (adjust if needed)
EXPOSE 8080
# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
