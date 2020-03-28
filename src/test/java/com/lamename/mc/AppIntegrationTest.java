package com.lamename.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lamename.mc.listeners.PlayerEventsListener;
import com.lamename.mc.models.PlayerScore;
import com.lamename.mc.repositories.PlayerScoreRepository;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.File;
import java.util.UUID;


/**
 * Unit test for simple App.
 */
public class AppIntegrationTest
    extends TestCase
{

    protected Injector injector;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppIntegrationTest( String testName )
    {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        injector = Guice.createInjector(new PlayerScoreTestJsonDbModule());
    }

    @Override
    protected void tearDown(){
        for(File file: new File("./testJsonDb").listFiles())
            if (!file.isDirectory()){
                file.delete();
            }
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(AppIntegrationTest.class);
    }

    public void testCreateAndLoadPlayerScore(){
        UUID randomUuid = UUID.randomUUID();
        PlayerScore score = new PlayerScore(randomUuid.toString());
        score.incrementDeathCount();
        PlayerScoreRepository repo = injector.getInstance(PlayerScoreRepository.class);
        repo.savePlayerScore(score);
        PlayerScore newlyCreated = repo.getPlayerScore(randomUuid);
        assertEquals(1, newlyCreated.getDeathCount());
    }

    public void testInjectorCanCreatePlayerEventsListener(){
        assertNotNull(injector.getInstance(PlayerEventsListener.class));
    }

}
