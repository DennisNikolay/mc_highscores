package com.lamename.mc.modules;

import com.google.inject.*;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.lamename.mc.App;
import com.lamename.mc.factories.PlayerScoreFactory;
import com.lamename.mc.models.PlayerScore;
import com.lamename.mc.repositories.PlayerScoreRepository;
import io.jsondb.JsonDBTemplate;

public class PlayerScoreJsonDbModule extends AbstractModule {

    protected JsonDBTemplate jsonDBTemplate;

    protected PlayerScoreRepository playerScoreRepo;

    @Override
    public void configure() {
        install(new FactoryModuleBuilder().build(PlayerScoreFactory.class));
        jsonDBTemplate = new JsonDBTemplate(getDbFilesPath(), "com.lamename.mc.models");
        if(!jsonDBTemplate.collectionExists(PlayerScore.class)){
            jsonDBTemplate.createCollection(PlayerScore.class);
        }
    }

    @Provides
    public PlayerScoreRepository getPlayerScoreRepo(Injector injector) {
        if (playerScoreRepo == null){
            playerScoreRepo = new PlayerScoreRepository(jsonDBTemplate, injector.getInstance(PlayerScoreFactory.class));
        }
        return playerScoreRepo;
    }

    protected String getDbFilesPath(){
        String path = "./plugins/"+App.pluginName+"/jsonDb";
        if (System.getenv("JSON_DB_FILEPATH") != null)
            path = System.getenv("JSON_DB_FILEPATH");
        return path;
    }

}
