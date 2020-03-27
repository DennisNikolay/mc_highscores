package com.lamename.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * Entrypoint for highscore app
 */
public class App extends JavaPlugin
{

    Injector injector;

    @Override
    public void onEnable() {
        //Thread.currentThread().setContextClassLoader(getClass().getClassLoader());
        injector = Guice.createInjector(new DatabaseModule());
    }

    @Override
    public void onDisable(){

    }
}
