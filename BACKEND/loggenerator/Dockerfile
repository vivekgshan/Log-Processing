# Stage 1: Use JDK runtime only (since JAR is already built by Jenkins/Maven)
FROM eclipse-temurin:17-jdk

# Set working directory inside container
WORKDIR /app

# Copy the JAR built by Maven
COPY Backend/loggenerator/target/logGenerator-*.jar app.jar

# Expose app port (matches application.properties → 8783)
EXPOSE 8783

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
