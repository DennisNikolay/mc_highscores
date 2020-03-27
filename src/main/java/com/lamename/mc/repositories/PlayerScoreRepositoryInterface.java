package com.lamename.mc.repositories;

import com.google.inject.ImplementedBy;
import com.lamename.mc.models.PlayerScore;

@ImplementedBy(PlayerScoreRepository.class)
public interface PlayerScoreRepositoryInterface {

    PlayerScore getPlayerScore(String playerListName);

    void savePlayerScore(PlayerScore score);

}
