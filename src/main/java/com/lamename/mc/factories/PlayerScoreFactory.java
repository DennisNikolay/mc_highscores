package com.lamename.mc.factories;

import com.lamename.mc.models.PlayerScore;

public class PlayerScoreFactory {

    public PlayerScore create(String uuid){
        return new PlayerScore(uuid);
    }

}
