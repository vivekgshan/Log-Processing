# Stage 1: Build stage (Maven inside container)
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only this service code
COPY pom.xml .
COPY src ./src

# Build the JAR inside the container
RUN mvn clean package -DskipTests

# Stage 2: Runtime stage
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy JAR from build stage (no dependency on host file system)
COPY --from=build /app/target/logGenerator-*.jar app.jar

# Expose application port (from application.properties → 8780)
EXPOSE 8780

ENTRYPOINT ["java", "-jar", "app.jar"]
