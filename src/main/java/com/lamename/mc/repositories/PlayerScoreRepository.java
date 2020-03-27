package com.lamename.mc.repositories;

import com.lamename.mc.models.PlayerScore;
import com.google.inject.persist.Transactional;
import javax.inject.Inject;
import javax.persistence.EntityManager;

public class PlayerScoreRepository implements PlayerScoreRepositoryInterface {

    protected EntityManager entityManager;

    @Inject PlayerScoreRepository(EntityManager em){
        entityManager = em;
    }

    @Transactional
    public PlayerScore getPlayerScore(String playerListName) {
        return entityManager.find(PlayerScore.class, playerListName);
    }

    @Transactional
    public void savePlayerScore(PlayerScore score) {
        entityManager.persist(score);
    }

}
