package com.lamename.mc.factories;

import com.lamename.mc.models.PlayerScore;

public interface PlayerScoreFactory {

    PlayerScore create(String uuid);

}
