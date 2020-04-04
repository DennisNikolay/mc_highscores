package com.lamename.mc.factories;

import com.lamename.mc.models.PlayerScore;
import org.bukkit.entity.Player;

public interface PlayerScoreFactory {

    PlayerScore create(Player player);

}
