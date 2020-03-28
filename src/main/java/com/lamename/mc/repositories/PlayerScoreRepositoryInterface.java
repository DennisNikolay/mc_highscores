package com.lamename.mc.repositories;

import com.lamename.mc.models.PlayerScore;

import java.util.UUID;

public interface PlayerScoreRepositoryInterface {

    PlayerScore getPlayerScore(UUID uuid);

    void savePlayerScore(PlayerScore score);

}
