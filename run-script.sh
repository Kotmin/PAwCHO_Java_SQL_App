#!/bin/bash


docker build . -t my_sql_server_for_java -f mysql/Dockerfile
docker run -p 3306:3306 -d my_sql_server_for_java --rm

docker build . -t java_console_CRUD_app

docker run -it --tty java_console_CRUD_app


docker stop my_sql_server_for_java
docker stop java_console_CRUD_app

echo "Script succesfully ended"
