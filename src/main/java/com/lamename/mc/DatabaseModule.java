package com.lamename.mc;

import com.google.inject.AbstractModule;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

import javax.inject.Inject;
import javax.inject.Singleton;

public class DatabaseModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new JpaPersistModule("docker"));
        bind(com.lamename.mc.DatabaseModule.JPAInitializer.class).asEagerSingleton();
    }

    @Singleton
    public static class JPAInitializer {

        @Inject
        public JPAInitializer(final PersistService service) {
            service.start();
        }

    }

}
