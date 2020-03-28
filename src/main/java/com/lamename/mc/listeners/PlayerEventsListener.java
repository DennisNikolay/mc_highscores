package com.lamename.mc.listeners;

import com.lamename.mc.models.PlayerScore;
import com.lamename.mc.repositories.PlayerScoreRepository;
import com.lamename.mc.repositories.PlayerScoreRepositoryInterface;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import org.bukkit.event.EventHandler;

import javax.inject.Inject;
import java.util.UUID;


public class PlayerEventsListener implements Listener {

    protected PlayerScoreRepositoryInterface repo;

    @Inject  public PlayerEventsListener(PlayerScoreRepository repo){
        this.repo = repo;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        incrementDeathCounter(event.getEntity().getUniqueId());
    }

    protected void incrementDeathCounter(UUID playerUUid){
        PlayerScore score = repo.getPlayerScore(playerUUid);
        score.incrementDeathCount();
        repo.savePlayerScore(score);
    }

}
