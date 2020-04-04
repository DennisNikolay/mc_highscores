package com.lamename.mc.commands;

import com.google.inject.Inject;
import com.lamename.mc.factories.HighscoreCommandFactory;
import com.lamename.mc.repositories.PlayerScoreRepository;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HighscoreCommandWrapper implements CommandExecutor {

    protected HighscoreCommand command;
    protected HighscoreCommandFactory factory;

    @Inject
    public HighscoreCommandWrapper(HighscoreCommandFactory commandFactory){
        factory = commandFactory;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        HighscoreCommand highscoreCommand = factory.create(new CommandSenderWrapper(commandSender), args);
        System.out.println("Executing command");
        return highscoreCommand.execute();
    }

}
