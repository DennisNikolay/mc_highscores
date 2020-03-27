package com.lamename.mc;

import com.google.inject.AbstractModule;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

import javax.inject.Inject;
import javax.inject.Singleton;

public class TestDatabaseModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new JpaPersistModule("test"));
        bind(com.lamename.mc.TestDatabaseModule.JPAInitializer.class).asEagerSingleton();
    }

    @Singleton
    public static class JPAInitializer {

        @Inject
        public JPAInitializer(final PersistService service) {
            service.start();
        }

    }

}