# Use an official OpenJDK image from the Docker Hub
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app
RUN ls
COPY target/logListenerCollector-3.5.5.jar app.jar

EXPOSE 8785

# Command to run the jar file
CMD ["java", "-jar", "app.jar"]
