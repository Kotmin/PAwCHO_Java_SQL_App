# # Use a Java 8 runtime as a parent image
# FROM openjdk:8-jdk-alpine AS builder

# # Set the working directory to /app
# WORKDIR /app

# COPY /src/Main.java /app/

# RUN javac Main.java

# RUN jar cvf jdb-app.jar Main.class



# FROM openjdk:8-jre-alpine AS java_build


# # ENV CLASSPATH /app/mysql-connector-java-8.0.27.jar

# # Copy the application jar file and mysql-connector-java driver to the container
# COPY --from=builder /app/jdb-app.jar /app/

# # Download the MySQL connector JAR file
# ENV MYSQL_DRIVER_VERSION="8.0.26"
# # https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.26.tar.gz

# RUN wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz
# RUN tar xvzf mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar --strip-components=1
# RUN rm mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz


# # Copy the JAR file to the appropriate directory
# COPY mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar /app/


# # Temp
# # RUN yes | wget -O mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar https://repo1.maven.org/maven2/mysql/mysql-connector-java/ ${MYSQL_DRIVER_VERSION}/mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar

# # RUN yes | wget -O https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz


# # https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz

# # RUN yes | wget https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.27/mysql-connector-java-8.0.27.jar -P /app

# # Copy the JAR file to the lib directory
# #RUN cp /tmp/mysql-connector-java-8.0.27.jar /usr/lib/jvm/java-1.8-openjdk/jre/lib/ext/


# #COPY lib/mysql-connector-java-8.0.27.jar /app
# # End of temp

# # Add the mysql-connector-java driver to the classpath
# ENV CLASSPATH /app/mysql-connector-java-8.0.26.jar

# # Run the application jar file
# CMD ["java", "-jar", "jdb-app.jar"]

# New

# FROM openjdk:8-jdk-alpine AS builder
# WORKDIR /app
# COPY /src/Main.java /src/MANIFEST.MF /app/
# RUN javac Main.java
# RUN jar cvfm jdb-app.jar MANIFEST.MF Main.class


# FROM openjdk:8-jre-alpine AS java_build
# COPY --from=builder /app/jdb-app.jar /app/
# COPY /src/mysql-connector-j-8.0.33.jar /app/
# ENV MYSQL_DRIVER_VERSION="8.0.27"
# RUN wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz && \
#     tar xvzf mysql-connector-java-${MYSQL_DRIVER_VERSION}.tar.gz --strip-components=1 --no-same-owner -C /tmp && \
#     cp /tmp/mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar /app/


# CMD ["java", "-cp", "/app/mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar:/app/jdb-app.jar", "Main"]
# ENV CLASSPATH /app/mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar

# Updated ENV
# ENV CLASSPATH /app/mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar:$CLASSPATH


# CMD ["java", "-jar", "/app/jdb-app.jar"]

# ENV CLASSPATH=/app/mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar:$CLASSPATH

# CMD ["java", "-cp", "/app/mysql-connector-java-${MYSQL_DRIVER_VERSION}.jar:/app/jdb-app.jar", "com.mysql.cj.jdbc.Driver", "jdb-app.jar"]



# ENV CLASSPATH=/app/mysql-connector-j-8.0.33.jar:$CLASSPATH

# CMD ["java", "-cp", "mysql-connector-j-8.0.33.jar:.","Main"]


# next of next


FROM openjdk:11
COPY ./src/ /
WORKDIR /
RUN javac Main.java
CMD ["java", "-cp", "mysql-connector-j-8.0.33.jar:.","Main"]



