package com.lamename.mc.repositories;

import com.google.inject.Inject;
import com.lamename.mc.factories.PlayerScoreFactory;
import com.lamename.mc.models.PlayerScore;
import io.jsondb.JsonDBTemplate;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class PlayerScoreRepository implements PlayerScoreRepositoryInterface {

    protected JsonDBTemplate dbTemplate;
    protected PlayerScoreFactory playerScoreFactory;

    public PlayerScoreRepository(){};

    @Inject
    public PlayerScoreRepository(JsonDBTemplate dbTemplate, PlayerScoreFactory psFactory){
        this.dbTemplate = dbTemplate;
        playerScoreFactory = psFactory;
    }

    @Override
    public PlayerScore getPlayerScore(Player player) {
        List<PlayerScore> result =  dbTemplate.find(String.format("/.[uuid='%s']", player.getUniqueId()), PlayerScore.class);
        return result.isEmpty() ? playerScoreFactory.create(player) : result.get(0);
    }

    @Override
    public void savePlayerScore(PlayerScore score) {
        dbTemplate.upsert(score);
    }

    @Override
    public List<PlayerScore> findAll(Comparator<PlayerScore> comp, String slice) {
        return dbTemplate.findAll(PlayerScore.class, comp, slice);
    }

    @Override
    public List<PlayerScore> findAll(Comparator<PlayerScore> comp) {
        return dbTemplate.findAll(PlayerScore.class, comp);
    }

    @Override
    public boolean exists(UUID uuid){
        return findById(uuid) != null;
    }

    @Override
    public PlayerScore findById(UUID uuid){
        List<PlayerScore> queryResult = dbTemplate.find(String.format("/.[uuid='%s']", uuid), PlayerScore.class);
        if(queryResult.size() == 0){
            return null;
        }
        return queryResult.get(0);
    }
}
