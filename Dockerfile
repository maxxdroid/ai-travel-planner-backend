# Use a lightweight JDK base image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory inside the container
WORKDIR /app

# Copy the JAR from the Maven target folder
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

#Expose Port 80880
EXPOSE 8080

# Command to run the Spring Boot UDP server
ENTRYPOINT ["java", "-jar", "app.jar"]