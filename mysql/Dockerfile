
FROM mysql:5.7


ENV MYSQL_ROOT_PASSWORD="root"

# Copy a custom MySQL configuration file into the container
# COPY my.cnf /etc/mysql/my.cnf

# Set the character set for the MySQL server
RUN echo "[mysqld]\ncharacter-set-server=utf8mb4" >> /etc/mysql/my.cnf


EXPOSE 3306


COPY init.sql /docker-entrypoint-initdb.d/
COPY create-table.sql /docker-entrypoint-initdb.d/

# Start the MySQL server when the container starts
CMD ["mysqld"]
