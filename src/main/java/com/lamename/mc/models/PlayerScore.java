package com.lamename.mc.models;

public class PlayerScore {

    public PlayerScore(){};

    protected String playerListName;

    protected int deathCount;

    public PlayerScore(String playerListName){
        this.playerListName = playerListName;
        deathCount = 0;
    }

    public int getDeathCount() {
        return deathCount;
    }

    public void incrementDeathCount(){
        deathCount++;
    }

}
