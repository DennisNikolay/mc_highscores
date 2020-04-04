package com.lamename.mc.factories;

import com.lamename.mc.commands.HighscoreCommand;
import com.lamename.mc.commands.MessageOutputStream;

public interface HighscoreCommandFactory {
    HighscoreCommand create(MessageOutputStream commandSender, String[] args);
}
