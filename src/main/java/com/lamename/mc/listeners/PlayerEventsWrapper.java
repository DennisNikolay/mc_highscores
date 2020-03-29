package com.lamename.mc.listeners;

import com.google.inject.Inject;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;


public class PlayerEventsWrapper implements Listener {

    PlayerEventsHandlerInterface handler;

    @Inject
    public PlayerEventsWrapper(PlayerEventsHandlerInterface playerEventsHandler){
        handler = playerEventsHandler;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        handler.handlePlayerDeath(event.getEntity());
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) { handler.handlePlayerJoint(event.getPlayer()); }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event){ handler.handlePlayerFished(event.getPlayer(), event.getCaught()); }
}
