# Use an official OpenJDK image from the Docker Hub
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app
RUN ls
COPY target/logWarnPersistor-3.5.5.jar app.jar

EXPOSE 8784

# Command to run the jar file
CMD ["java", "-jar", "app.jar"]
