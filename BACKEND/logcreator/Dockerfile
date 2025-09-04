# Stage 1: Build the full app
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn -pl BACKEND/logcreator -am clean package -DskipTests

# Stage 2: Run the logcreator JAR
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/BACKEND/logcreator/target/logcreator-*.jar /app/logcreator.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "logcreator.jar"]
