FROM postgres:12.0
RUN mkdir -p /home/minecraft/sql
COPY sql/ /home/minecraft/sql/
RUN ls /home/minecraft/sql
COPY init.sh /docker-entrypoint-initdb.d/init.sh