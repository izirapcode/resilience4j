# Use OpenJDK as the base image
FROM openjdk:17
WORKDIR /app

# Copy JAR file from the respective service directory (context-based)
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
