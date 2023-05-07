#!/bin/bash


docker build . -t my_sql_server_for_java -f mysql/Dockerfile
docker run -p 3306:3306 -d --name my_db --rm my_sql_server_for_java 

docker build . -t java_console_crud_app

docker run -it --tty --name java_console_crud_app --rm java_console_crud_app


docker stop my_sql_server_for_java
docker stop java_console_crud_app

echo "Script ended"
