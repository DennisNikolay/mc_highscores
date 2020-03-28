package com.lamename.mc.models;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "player_score", schemaVersion= "1.0")
public class PlayerScore {

    @Id
    protected String uuid;

    protected int deathCount;

    public PlayerScore(){};

    public PlayerScore(String uuid){
        this.uuid = uuid;
        deathCount = 0;
    }

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

}
