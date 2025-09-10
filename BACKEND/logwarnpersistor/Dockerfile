# Use an official OpenJDK image from the Docker Hub
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Install PostgreSQL client
RUN apt-get update && \
    apt-get install -y --no-install-recommends postgresql-client && \
    rm -rf /var/lib/apt/lists/*
    
RUN ls
COPY target/logWarnPersistor-3.5.5.jar app.jar

EXPOSE 8784

# Command to run the jar file
CMD ["java", "-jar", "app.jar"]
