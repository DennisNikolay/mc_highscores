package com.lamename.mc.modules;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.lamename.mc.factories.HighscoreCommandFactory;

public class CommandModule extends AbstractModule {

    @Override
    public void configure() {
        install(new FactoryModuleBuilder().build(HighscoreCommandFactory.class));
    }

}
