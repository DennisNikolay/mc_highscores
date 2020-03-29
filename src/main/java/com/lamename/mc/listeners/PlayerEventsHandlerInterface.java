package com.lamename.mc.listeners;

import com.google.inject.ImplementedBy;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

@ImplementedBy(PlayerEventsHandler.class)
public interface PlayerEventsHandlerInterface {

    void handlePlayerJoint(Player p);
    void handlePlayerDeath(Player p);
    void handlePlayerFished(Player p, Entity fishedObject);

}
