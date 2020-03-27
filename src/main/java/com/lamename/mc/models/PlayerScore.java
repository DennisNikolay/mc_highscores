package com.lamename.mc.models;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class PlayerScore {

    @Id
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
