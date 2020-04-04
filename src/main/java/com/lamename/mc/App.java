package com.lamename.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lamename.mc.commands.HighscoreCommandWrapper;
import com.lamename.mc.listeners.PlayerEventsWrapper;
import com.lamename.mc.modules.CommandModule;
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
        injector = Guice.createInjector(new PlayerScoreJsonDbModule(), new CommandModule());
        getServer().getPluginManager().registerEvents(injector.getInstance(PlayerEventsWrapper.class), this);
        this.getCommand("highscore").setExecutor(injector.getInstance(HighscoreCommandWrapper.class));
    }

    @Override
    public void onDisable(){

    }
}
