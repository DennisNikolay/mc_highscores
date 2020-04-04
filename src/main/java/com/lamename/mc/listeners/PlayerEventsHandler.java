package com.lamename.mc.listeners;

import com.google.inject.Inject;
import com.lamename.mc.models.PlayerScore;
import com.lamename.mc.repositories.PlayerScoreRepositoryInterface;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PlayerEventsHandler implements PlayerEventsHandlerInterface {
    protected PlayerScoreRepositoryInterface repo;

    @Inject
    PlayerEventsHandler(PlayerScoreRepositoryInterface playerScoreRepository){
        repo = playerScoreRepository;
    }

    public void incrementDeathCounter(Player player){
        PlayerScore score = repo.getPlayerScore(player);
        score.incrementDeathCount();
        repo.savePlayerScore(score);
    }

    public void createNewEntryInScores(Player player){
        if(!repo.exists(player.getUniqueId())) {
            PlayerScore newScore = repo.getPlayerScore(player);
            repo.savePlayerScore(newScore);
        }
    }

    public void incrementFishedCounter(Player player){
        PlayerScore score = repo.getPlayerScore(player);
        score.incrementFishedCount();
        repo.savePlayerScore(score);
    }

    @Override
    public void handlePlayerJoint(Player p) {
        createNewEntryInScores(p);
    }

    @Override
    public void handlePlayerDeath(Player p) {
        incrementDeathCounter(p);
    }

    @Override
    public void handlePlayerFished(Player p, Entity fishedObject) {
        if(fishedObject != null){
            incrementFishedCounter(p);
        }
    }
}
