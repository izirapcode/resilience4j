# Use OpenJDK as the base image
FROM openjdk:17
WORKDIR /app

# Copy JAR file from the respective service directory (context-based)
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

# Run the application
ENV JAVA_OPTS="-Dserver.tomcat.threads.max=10 -Dserver.tomcat.threads.min-spare=10 -XX:ActiveProcessorCount=2"
ENTRYPOINT ["java", "-jar", "app.jar"]
