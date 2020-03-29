package com.lamename.mc.models;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "player_score", schemaVersion= "1.0")
public class PlayerScore {

    @Id
    protected String uuid;

    protected String playerName;

    protected int deathCount;

    protected int fishedItemsCount;

    public PlayerScore(){};


    public String getUuid(){
        return uuid;
    }

    public void setUuid(String uuid){
        this.uuid = uuid;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void incrementDeathCount(){
        deathCount++;
    }

    public int getFishedItemsCount() {
        return fishedItemsCount;
    }

    public void incrementFishedCount(){
        fishedItemsCount++;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}
