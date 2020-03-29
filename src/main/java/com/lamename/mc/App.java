package com.lamename.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lamename.mc.commands.HighscoreCommandExecutor;
import com.lamename.mc.listeners.PlayerEventsWrapper;
import com.lamename.mc.modules.PlayerScoreJsonDbModule;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Entrypoint for highscore app
 */
public class App extends JavaPlugin
{

    static final public String pluginName = "mc_highscores";

    Injector injector;

    @Override
    public void onEnable() {
        injector = Guice.createInjector(new PlayerScoreJsonDbModule());
        getServer().getPluginManager().registerEvents(injector.getInstance(PlayerEventsWrapper.class), this);
        this.getCommand("highscores").setExecutor(injector.getInstance(HighscoreCommandExecutor.class));
    }

    @Override
    public void onDisable(){

    }
}
