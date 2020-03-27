package com.lamename.mc.listeners;

import com.lamename.mc.models.PlayerScore;
import com.lamename.mc.repositories.PlayerScoreRepositoryInterface;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import org.bukkit.event.EventHandler;
import javax.inject.Inject;

public class PlayerEventsListener implements Listener {

    @Inject protected PlayerScoreRepositoryInterface repo;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        String playerName = event.getEntity().getPlayerListName();
        PlayerScore score = repo.getPlayerScore(playerName.toString());
        if(score == null){
            score = new PlayerScore(playerName);
        }
        score.incrementDeathCount();
        repo.savePlayerScore(score);
    }

}
