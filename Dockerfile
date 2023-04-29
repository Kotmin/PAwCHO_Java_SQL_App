# Use a Java 8 runtime as a parent image
FROM openjdk:8-jre-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the application jar file and mysql-connector-java driver to the container
COPY target/jdb-app.jar /app
COPY lib/mysql-connector-java-8.0.27.jar /app

# Add the mysql-connector-java driver to the classpath
ENV CLASSPATH /app/mysql-connector-java-8.0.27.jar

# Run the application jar file
CMD ["java", "-jar", "jdb-app.jar"]
