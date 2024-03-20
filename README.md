# Java & MySQL Dockerized Application

## Introduction

This project, prepared by Paweł Jan Tłusty, demonstrates a Dockerized environment for running a Java application with a MySQL database. It's designed to address common challenges in containerizing Java applications and their communication with MySQL databases, offering a streamlined setup for development and testing.

## Prerequisites

- Docker
- Docker Compose (for managing multi-container Docker applications)
- Java JDK (for running and compiling the Java code)
- MySQL Connector/J (for database connection)

Relevant issues and solutions can be found at:
- [Moby/Moby Issue #25562](https://github.com/moby/moby/issues/25562)
- [Deepak Sureshkumar's LinkedIn Article on Dockerized Java with MySQL](https://www.linkedin.com/pulse/running-java-application-mysql-linked-docker-deepak-sureshkumar/)

## Setup Instructions

```bash
chmod +x run-script.sh
./run-script.sh
```

### Network Creation

To avoid linking containers directly, we script creates a special network for them. This approach facilitates cleaner and more manageable container intercommunication.


### MySQL Container

The MySQL container setup involves:
- A Dockerfile with environmental variables to ease database operations.
- Scripts to ensure the desired databases and tables exist, enabling future volume attachment for persistent data storage.

### Java Application Container

The Dockerfile for the Java application includes:
- Comments on attempts to utilize multi-stage building for selecting and downloading the desired version of MySQL Connector/J.
- The source code and Dockerfile are located in the `/src` directory, except for the Dockerfile itself.

## Java Code Overview

The Java application features a simple CRUD (Create, Read, Update, Delete) structure divided across three classes:
- `Main` handles user interaction.
- `TaskController` manages database communication.
- Potential areas for refactoring include removing code redundancy, externalizing login data, and adding functionality to display all records.

## Conclusion

This laboratory project provides a foundational setup for a Dockerized Java and MySQL application. While focused on practical setup rather than code elegance, the project offers a solid base for further development and learning in cloud application programming.

For a complete change history and detailed exploration of trial and error, refer to the commit history in the project's GitHub repository.
