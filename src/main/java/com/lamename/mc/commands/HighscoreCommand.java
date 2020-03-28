package com.lamename.mc.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HighscoreCommand extends Command {

    protected HighscoreCommand(String name) {
        super(name);
    }

    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        String score = "*";
        int page = 1;
        if(strings.length > 0){
            if(strings[0].equals("me")){

            }else{

            }

        }
        return false;
    }



}
