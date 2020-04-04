package com.lamename.mc;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lamename.mc.commands.HighscoreCommand;
import com.lamename.mc.commands.HighscoreCommandWrapper;
import com.lamename.mc.commands.MessageOutputStream;
import com.lamename.mc.factories.HighscoreCommandFactory;
import com.lamename.mc.listeners.PlayerEventsWrapper;
import com.lamename.mc.models.PlayerScore;
import com.lamename.mc.modules.CommandModule;
import com.lamename.mc.modules.PlayerScoreJsonDbModule;
import com.lamename.mc.repositories.PlayerScoreRepository;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.UUID;
import static org.mockito.Mockito.*;


/**
 * Unit test for simple App.
 */
public class AppIntegrationTest extends TestCase
{

    class PlayerScoreTestJsonDbModule extends PlayerScoreJsonDbModule {
        @Override
        protected String getDbFilesPath() {
            return "./testJsonDb";
        }
    }


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
        injector = Guice.createInjector(new PlayerScoreTestJsonDbModule(), new CommandModule());
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
        Player player = mock(Player.class);
        when(player.getUniqueId()).thenReturn(randomUuid);
        PlayerScore score = new PlayerScore(player);
        score.incrementDeathCount();
        PlayerScoreRepository repo = injector.getInstance(PlayerScoreRepository.class);
        repo.savePlayerScore(score);
        PlayerScore newlyCreated = repo.findById(randomUuid);
        assertNotNull(newlyCreated);
        assertEquals(1, newlyCreated.getDeathCount());
    }

    public void testInjectorCanCreatePlayerEventsListener(){
        assertNotNull(injector.getInstance(PlayerEventsWrapper.class));
    }

    public void testInjectorCanCreateHighscoreCommandHandler(){
        assertNotNull(injector.getInstance(HighscoreCommandWrapper.class));
    }

    public void testInjectorCanCreateHighscoreCommandUsingFacotry(){
        HighscoreCommandFactory factory = injector.getInstance(HighscoreCommandFactory.class);
        assertNotNull(factory);
        HighscoreCommand command = factory.create(mock(MessageOutputStream.class), new String[0]);
        assertNotNull(command);
    }

    public void testInjectorCanCreatePlayerEventsWrapper(){
        assertNotNull(injector.getInstance(PlayerEventsWrapper.class));
    }
}
