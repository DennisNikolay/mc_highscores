package com.lamename.mc.repositories;

import com.lamename.mc.factories.PlayerScoreFactory;
import com.lamename.mc.models.PlayerScore;
import io.jsondb.JsonDBTemplate;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class PlayerScoreRepository implements PlayerScoreRepositoryInterface {

    protected JsonDBTemplate dbTemplate;
    protected PlayerScoreFactory playerScoreFactory;

    @Inject
    public PlayerScoreRepository(JsonDBTemplate dbTemplate, PlayerScoreFactory psFactory){
        this.dbTemplate = dbTemplate;
        playerScoreFactory = psFactory;
    }

    public PlayerScore getPlayerScore(UUID uuid){
        return getPlayerScore(uuid.toString());
    }

    public PlayerScore getPlayerScore(String uuid) {
        List<PlayerScore> result =  dbTemplate.find(String.format("/.[uuid='%s']", uuid), PlayerScore.class);
        return result.isEmpty() ? playerScoreFactory.create(uuid) : result.get(0);
    }

    public void savePlayerScore(PlayerScore score) {
        dbTemplate.upsert(score);
    }

}
