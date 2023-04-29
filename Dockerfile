# Use a Java 8 runtime as a parent image
FROM openjdk:8-jre-alpine AS builder

# Set the working directory to /app
WORKDIR /app

COPY ./src/Main.java /app/

RUN javac Main.java; \
jar cfe jdb-app.jar Main Main.class



FROM openjdk:8-jre-alpine AS java_build


ENV CLASSPATH /app/mysql-connector-java-8.0.27.jar

# Copy the application jar file and mysql-connector-java driver to the container
COPY --from=builder /app/jdb-app.jar /app
COPY lib/mysql-connector-java-8.0.27.jar /app

# Add the mysql-connector-java driver to the classpath
ENV CLASSPATH /app/mysql-connector-java-8.0.27.jar

# Run the application jar file
CMD ["java", "-jar", "jdb-app.jar"]
