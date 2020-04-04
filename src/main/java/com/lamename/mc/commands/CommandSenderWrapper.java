package com.lamename.mc.commands;

import org.bukkit.command.CommandSender;

public class CommandSenderWrapper implements MessageOutputStream{

    CommandSender sender;

    public CommandSenderWrapper(CommandSender sender){
        this.sender = sender;
    }

    public void sendMessage(String msg){
        sender.sendMessage(msg);
    }



}
