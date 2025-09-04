# Stage 1: Build the Java app (logcreator module only)
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the whole repo (so parent POM is available)
COPY . .

# Run Maven from repo root, build only logcreator
WORKDIR /app
RUN mvn -f pom.xml -pl BACKEND/logcreator -am clean package -DskipTests

# Stage 2: Run the logcreator JAR with OpenJDK
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/BACKEND/logcreator/target/logcreator-*.jar /app/logcreator.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "logcreator.jar"]
