# Stage 1: Build the Java app using Maven
FROM maven:3.8.6-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the JAR with OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app
# Copy and rename the built JAR
COPY --from=build /app/target/logcreator-*.jar /app/logcreator.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "logcreator.jar"]
