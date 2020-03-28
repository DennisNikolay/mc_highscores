package com.lamename.mc;

import com.lamename.mc.models.PlayerScore;
import com.lamename.mc.modules.PlayerScoreJsonDbModule;

public class PlayerScoreTestJsonDbModule extends PlayerScoreJsonDbModule {

    @Override
    public void configure() {
        super.configure();
        jsonDBTemplate.createCollection(PlayerScore.class);
    }

    @Override
    protected String getDbFilesPath() {
        return "./testJsonDb";
    }
}
