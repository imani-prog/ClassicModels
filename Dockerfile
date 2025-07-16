# Use an OpenJDK 17 image as a base
FROM maven:3.8.4-openjdk-17 AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .

# Download and cache dependencies
RUN mvn dependency:go-offline -B

# Copy the application source code
COPY src ./src

# Build the application
RUN mvn package -DskipTests

# Use a lightweight base image for the runtime environment
FROM openjdk:17-jdk-alpine

# Create a non-root user and group
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Set the working directory in the container
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/target/*.jar /app/classicmodels.jar

# Change ownership of application files
RUN chown -R appuser:appgroup /app

# Switch to the non-root user
USER appuser

# Expose the port your application runs on
EXPOSE 8081

# Command to run the application
CMD ["java", "-jar", "classicmodels.jar"]
