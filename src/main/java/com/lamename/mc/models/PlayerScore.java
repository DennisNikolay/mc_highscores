package com.lamename.mc.models;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;
import org.bukkit.entity.Player;

@Document(collection = "player_score", schemaVersion= "1.0")
public class PlayerScore {

    @Id
    protected String uuid;

    protected String playerName;

    protected int deathCount;

    protected int fishedItemsCount;

    public PlayerScore(){}

    @AssistedInject
    public PlayerScore(@Assisted Player player){
        this.uuid = player.getUniqueId().toString();
        this.playerName = player.getPlayerListName();
    };

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
