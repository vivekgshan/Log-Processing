# Use lightweight JDK image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the built JAR from Jenkins build stage
COPY target/logGenerator-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8783

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
