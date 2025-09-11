# Stage 1: Build the JAR using Maven
FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy only loggenerator service code
COPY pom.xml .
COPY src ./src

# Package JAR
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy JAR from build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8780

ENTRYPOINT ["java", "-jar", "app.jar"]
