# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/myapp.jar /app/myapp.jar

# Expose the port your app listens on (change if needed)
EXPOSE 8080

# Run the JAR file when the container starts
CMD ["java", "-jar", "myapp.jar"]
