package com.lamename.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Entrypoint for highscore app
 */
public class App extends JavaPlugin
{

    @Singleton
    private static class JpaInitializer {
        @Inject
        public JpaInitializer(final PersistService service) {
            service.start();
        }
    }

    @Override
    public void onEnable() {
        Injector injector = Guice.createInjector(new DatabaseModule());
    }

    @Override
    public void onDisable(){

    }
}
