package com.lamename.mc;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.lamename.mc.commands.HighscoreCommand;
import com.lamename.mc.commands.MessageOutputStream;
import com.lamename.mc.factories.HighscoreCommandFactory;
import com.lamename.mc.models.PlayerScore;
import com.lamename.mc.modules.CommandModule;
import com.lamename.mc.repositories.PlayerScoreRepositoryInterface;
import junit.framework.TestCase;
import org.mockito.ArgumentCaptor;

import java.util.LinkedList;
import java.util.List;
import static org.mockito.Mockito.*;

public class HighscoreCommandTest extends TestCase {

    class TestModule extends AbstractModule{
        @Override
        protected void configure() {
            super.configure();
            List<PlayerScore> mockedScores = getPlayerScoresMock();
            PlayerScoreRepositoryInterface repoMock = mock(PlayerScoreRepositoryInterface.class);
            when(repoMock.findAll(any())).thenReturn(mockedScores);
            when(repoMock.findAll(anyObject(), anyString()))
                    .thenAnswer(
                            call -> mockedScores.subList(
                                    Integer.valueOf(((String)call.getArguments()[1]).split(":")[0]),
                                    Integer.valueOf(((String)call.getArguments()[1]).split(":")[1])
                            )
                    );
            bind(PlayerScoreRepositoryInterface.class).toInstance(repoMock);
        }
    }

    protected Injector injector;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        injector = Guice.createInjector(new TestModule(), new CommandModule());
    }

    public void testPrintAllTop(){
        MessageOutputStream messageOutputStreamMock = mock(MessageOutputStream.class);
        ArgumentCaptor<String> msgCaptor = ArgumentCaptor.forClass(String.class);
        HighscoreCommandFactory factory = injector.getInstance(HighscoreCommandFactory.class);
        HighscoreCommand cmd = factory.create(messageOutputStreamMock, new String[0]);
        cmd.execute();
        verify(messageOutputStreamMock, times(30)).sendMessage(msgCaptor.capture());
        String expectedResult = "[, XxXpRoGamErXxX (least deaths):, --------------------------------------------------, , 1. Player 38 - 38 deaths, 2. Player 37 - 37 deaths, 3. Player 36 - 36 deaths, 4. Player 35 - 35 deaths, 5. Player 34 - 34 deaths, 6. Player 33 - 33 deaths, 7. Player 32 - 32 deaths, 8. Player 31 - 31 deaths, 9. Player 30 - 30 deaths, 10. Player 29 - 29 deaths, --------------------------------------------------, , King of the iron rod (most items fished):, --------------------------------------------------, , 0. Player 38 - 38 items fished, 1. Player 37 - 37 items fished, 2. Player 36 - 36 items fished, 3. Player 35 - 35 items fished, 4. Player 34 - 34 items fished, 5. Player 33 - 33 items fished, 6. Player 32 - 32 items fished, 7. Player 31 - 31 items fished, 8. Player 30 - 30 items fished, 9. Player 29 - 29 items fished, --------------------------------------------------]";
        assertEquals(expectedResult, msgCaptor.getAllValues().toString());
    }

    protected List<PlayerScore> getPlayerScoresMock(){
        int rowsToGenerate = HighscoreCommand.ENTRIES_PER_PAGE * 3 + HighscoreCommand.ENTRIES_PER_PAGE - 1;
        LinkedList<PlayerScore> resultList = new LinkedList<PlayerScore>();
        for(int i = 0; i < rowsToGenerate; i++){
            PlayerScore score = new PlayerScore();
            score.setUuid(String.valueOf(i));
            for(int j = 0; j < i; j++){
                score.incrementFishedCount();
                score.incrementDeathCount();
            }
            score.setPlayerName("Player ".concat(String.valueOf(i)));
            resultList.push(score);
        }
        return resultList;
    }

}
