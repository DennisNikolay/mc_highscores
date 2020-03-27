#!/bin/bash
#db
psql -U minecraft -d minecraft -f '/home/minecraft/sql/createPlayerScoreTable.sql'
#test db
psql -U minecraft -f '/home/minecraft/sql/createTestDb.sql'
psql -U minecraft -d minecraft_test -f '/home/minecraft/sql/createPlayerScoreTable.sql'
psql -U minecraft -d minecraft_test -f '/home/minecraft/sql/insertTestData.sql'
