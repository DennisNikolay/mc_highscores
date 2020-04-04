package com.lamename.mc.commands;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.lamename.mc.models.PlayerScore;
import com.lamename.mc.repositories.PlayerScoreRepositoryInterface;
import org.bukkit.entity.Player;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class HighscoreCommand{

    public static final int ENTRIES_PER_PAGE = 10;

    static final String HIGHSCORE_DIVIDER = "--------------------------------------------------";

    PlayerScoreRepositoryInterface repo;

    MessageOutputStream outStream;
    String pageArg;
    String scoreNameArg;

    HashMap<String, String> slicers = new HashMap<String, String>();
    HashMap<String, Comparator<PlayerScore>> queries = new HashMap<String, Comparator<PlayerScore>>();

    @Inject
    public HighscoreCommand(@Assisted MessageOutputStream outStream, @Assisted String[] args, PlayerScoreRepositoryInterface playerScoreRepo) {
        this.outStream = outStream;
        repo = playerScoreRepo;
        if(args.length == 0){
            scoreNameArg = "*";
            pageArg = "1";
        }
        if (args.length > 0){
            scoreNameArg = args[0];
            pageArg = "1";
        }
        if (args.length > 1)
            pageArg = args[1];
    }

    public boolean execute(){
        printHighscores();
        return !queries.isEmpty();
    }

    protected void printHighscores() {
        buildQueriesAndSlicers();
        if (queries.isEmpty())
            return;
        for (Map.Entry<String, Comparator<PlayerScore>> entry : queries.entrySet()) {
            String highscoreName = entry.getKey();
            List<PlayerScore> rowsToPrint = repo.findAll(entry.getValue(), slicers.get(entry.getKey()));
            switch (highscoreName) {
                case "death":
                    printDeathScore(rowsToPrint);
                    break;
                case "fish":
                    printFishScore(rowsToPrint);
                    break;
                case "*":
                    printDeathScore(rowsToPrint);
                    printFishScore(rowsToPrint);
                    break;
            }
        }
    }

    protected void printDeathScore(List<PlayerScore> rowsToPrint) {
        outStream.sendMessage("");
        outStream.sendMessage("XxXpRoGamErXxX (least deaths):");
        outStream.sendMessage(HIGHSCORE_DIVIDER);
        outStream.sendMessage("");
        for (int i = 0; i < ENTRIES_PER_PAGE && i < rowsToPrint.size(); i++) {
            outStream.sendMessage(String.format("%d. %s - %d deaths", i+1, rowsToPrint.get(i).getPlayerName(), rowsToPrint.get(i).getDeathCount()));
        }
        outStream.sendMessage(HIGHSCORE_DIVIDER);
    }

    protected void printFishScore(List<PlayerScore> rowsToPrint){
        outStream.sendMessage("");
        outStream.sendMessage("King of the iron rod (most items fished):");
        outStream.sendMessage(HIGHSCORE_DIVIDER);
        outStream.sendMessage("");
        for (int i = 0; i < ENTRIES_PER_PAGE && i < rowsToPrint.size(); i++) {
            outStream.sendMessage(String.format("%d. %s - %d items fished", i, rowsToPrint.get(i).getPlayerName(), rowsToPrint.get(i).getFishedItemsCount()));
        }
        outStream.sendMessage(HIGHSCORE_DIVIDER);
    }

    protected void buildQueriesAndSlicers() {
        buildQueries();
        if (pageArg.equals("me")) {
            buildMeSlicer((Player) outStream);
        } else {
            for (String key : queries.keySet())
                buildPageSlicer(key, pageArg);
        }
    }

    protected void buildQueries() {
        switch (scoreNameArg.toLowerCase()) {
            case "death":
                buildDeathQuery();
                break;
            case "fish":
                buildFishedQuery();
                break;
            case "*":
                buildDeathQuery();
                buildFishedQuery();
                break;
            default:
                outStream.sendMessage("Unknown ranking, only death, fish or * are valid");
                break;
        }
    }

    protected void buildDeathQuery() {
        queries.put("fish", Comparator.comparingInt(PlayerScore::getDeathCount));
    }

    protected void buildFishedQuery() {
        queries.put("death", Comparator.comparingInt(PlayerScore::getFishedItemsCount));
    }

    protected void buildMeSlicer(Player player) {
        if (!(outStream instanceof Player)) {
            outStream.sendMessage("Only players can use 'me' as page reference.");
            return;
        }
        for (Map.Entry<String, Comparator<PlayerScore>> query : queries.entrySet()) {
            PlayerScore[] highscoreEntries = (PlayerScore[]) repo.findAll(query.getValue()).toArray();
            String pageNo = "1";
            for (int i = 0; i * ENTRIES_PER_PAGE < highscoreEntries.length; i++) {
                for (int j = 0; j < ENTRIES_PER_PAGE; j++) {
                    if (i * ENTRIES_PER_PAGE + j < highscoreEntries.length) {
                        if (highscoreEntries[i * ENTRIES_PER_PAGE + j].getUuid().equals(player.getUniqueId().toString()))
                            pageNo = String.valueOf(i);
                    }
                }
            }
            buildPageSlicer(query.getKey(), pageNo);
        }
    }

    protected void buildPageSlicer(String queryKey, String pageNo) {
        try {
            int pageNr = Integer.valueOf(pageNo);
            if (pageNr <= 0) throw new NumberFormatException();
            slicers.put(queryKey, String.format("%d:%d", (pageNr - 1) * ENTRIES_PER_PAGE, pageNr * ENTRIES_PER_PAGE));
        } catch (NumberFormatException e) {
            outStream.sendMessage("Only numbers (greater 0) or 'me' are valid pages.");
        }
    }

}