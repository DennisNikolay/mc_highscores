#!/bin/bash
psql -c "CREATE USER minecraft WITH SUPERUSER PASSWORD '1234test';"
#db
createdb -O minecraft -E UTF8 minecraft
psql -U minecraft -d minecraft -f '/home/minecraft/sql/createPlayerScoreTable.sql'
#test db
createdb -O minecraft -E UTF8 minecraft_test
psql -U minecraft -d minecraft_test -f '/home/minecraft/sql/createPlayerScoreTable.sql'
psql -U minecraft -d minecraft_test -f '/home/minecraft/sql/insertTestData.sql'
