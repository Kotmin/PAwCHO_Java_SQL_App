# Use a Java 8 runtime as a parent image
FROM openjdk:8-jdk-alpine AS builder

# Set the working directory to /app
WORKDIR /app

COPY /src/Main.java /app/

RUN javac Main.java

RUN jar cvf jdb-app.jar Main.class



FROM openjdk:8-jre-alpine AS java_build


ENV CLASSPATH /app/mysql-connector-java-8.0.27.jar

# Copy the application jar file and mysql-connector-java driver to the container
COPY --from=builder /app/jdb-app.jar /app

# Download the MySQL connector JAR file
ENV MYSQL_DRIVER_VERSION="8.0.15"

RUN wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz && \
    tar xvzf mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz mysql-connector-java-${MYSQL_DRIVER_VERSION}/mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar --strip-components=1 && \
    rm mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz

# Copy the JAR file to the appropriate directory
COPY mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar /app

# RUN yes | wget -O mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/ ${MYSQL_DRIVER_VERSION}/mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar

# RUN yes | wget -O https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz


# https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz

# RUN yes | wget https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.27/mysql-connector-java-8.0.27.jar -P /app

# Copy the JAR file to the lib directory
#RUN cp /tmp/mysql-connector-java-8.0.27.jar /usr/lib/jvm/java-1.8-openjdk/jre/lib/ext/


#COPY lib/mysql-connector-java-8.0.27.jar /app

# Add the mysql-connector-java driver to the classpath
ENV CLASSPATH /app/mysql-connector-java-8.0.27.jar

# Run the application jar file
CMD ["java", "-jar", "jdb-app.jar"]
