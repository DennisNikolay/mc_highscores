export PGPASSWORD=1234test
psql -h localhost -p 42020 -U minecraft -d minecraft_test -f ./postgres/sql/insertTestData.sql
mvn install
rm ./data/plugins/highscores-1.0-SNAPSHOT.jar
docker-compose restart minecraft