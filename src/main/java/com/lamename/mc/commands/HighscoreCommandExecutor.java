package com.lamename.mc.commands;

import com.google.inject.Inject;
import com.lamename.mc.models.PlayerScore;
import com.lamename.mc.repositories.PlayerScoreRepositoryInterface;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;



public class HighscoreCommandExecutor implements CommandExecutor {

    public static final int ENTRIES_PER_PAGE = 10;

    static final String HIGHSCORE_DIVIDER = "--------------------------------------------------";

    PlayerScoreRepositoryInterface repo;

    CommandSender commandSender;
    String pageArg;
    String scoreNameArg;

    HashMap<String, String> slicers;
    HashMap<String, Comparator<PlayerScore>> queries;

    @Inject
    HighscoreCommandExecutor(PlayerScoreRepositoryInterface playerScoreRepo) {
        repo = playerScoreRepo;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        this.commandSender = commandSender;
        if (args.length > 0)
            pageArg = args[0];
        if (args.length > 1)
            pageArg = args[1];
        printHighscores();
        return !queries.isEmpty();
    }

    protected void printHighscores() {
        buildQueriesAndSlicers();
        if (queries.isEmpty())
            return;
        for (Map.Entry<String, Comparator<PlayerScore>> entry : queries.entrySet()) {
            String highscoreName = entry.getKey();
            PlayerScore[] rowsToPrint = (PlayerScore[]) repo.findAll(entry.getValue(), slicers.get(entry.getKey())).toArray();
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

    protected void printDeathScore(PlayerScore[] rowsToPrint) {
        commandSender.sendMessage("");
        commandSender.sendMessage("XxXpRoGamErXxX (least deaths):");
        commandSender.sendMessage(HIGHSCORE_DIVIDER);
        commandSender.sendMessage("");
        for (int i = 0; i < ENTRIES_PER_PAGE && i < rowsToPrint.length; i++) {
            commandSender.sendMessage(String.format("%d. %s - %d deaths", i, rowsToPrint[i].getPlayerName(), rowsToPrint[i].getDeathCount()));
        }
        commandSender.sendMessage(HIGHSCORE_DIVIDER);
    }

    protected void printFishScore(PlayerScore[] rowsToPrint){
        commandSender.sendMessage("");
        commandSender.sendMessage("King of the iron rod (most items fished):");
        commandSender.sendMessage(HIGHSCORE_DIVIDER);
        commandSender.sendMessage("");
        for (int i = 0; i < ENTRIES_PER_PAGE && i < rowsToPrint.length; i++) {
            commandSender.sendMessage(String.format("%d. %s - %d items fished", i, rowsToPrint[i].getPlayerName(), rowsToPrint[i].getFishedItemsCount()));
        }
        commandSender.sendMessage(HIGHSCORE_DIVIDER);
    }

    protected void buildQueriesAndSlicers() {
        buildQueries();
        if (pageArg.equals("me")) {
            buildMeSlicer((Player) commandSender);
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
                commandSender.sendMessage("Unknown ranking, only death, fish or * are valid");
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
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use 'me' as page reference.");
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
            commandSender.sendMessage("Only numbers (greater 0) or 'me' are valid pages.");
        }
    }

}