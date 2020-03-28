package com.lamename.mc.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HighscoreCommandExecutor {

    CommandSender commandSender;
    String pageArg;
    String scoreNameArg;

    String query;

    HighscoreCommandExecutor(CommandSender commandSender, String[] args) {
        this.commandSender = commandSender;
        if(args.length > 0 )
            pageArg = args[0];
        if(args.length > 1)
            pageArg = args[1];
    }

    protected String buildQuery() {
        if (pageArg.equals("me")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage("Only players can use 'me' as page reference.");
                return null;
            }
            buildMeQuery((Player) commandSender);
        } else {
            try {
                int pageNumber = Integer.valueOf(pageArg);
                buildPageQuery(pageNumber);
            } catch (NumberFormatException e) {
                commandSender.sendMessage("Only numbers or 'me' are valid pages.");
                return null;
            }
        }
        return null;

    }

    protected String buildMeQuery(Player player) {
        query = String.format("/.[playerListName='%s']", player.getPlayerListName());
        return null;

    }

    protected String buildPageQuery(int pageNumber) {
        return null;

    }

}