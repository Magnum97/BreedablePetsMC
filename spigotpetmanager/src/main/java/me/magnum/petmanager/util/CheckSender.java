package me.magnum.petmanager.util;

import org.bukkit.block.CommandBlock;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class CheckSender {


    public static boolean isPlayer(CommandSender sender) {
        return (sender instanceof Player);
    }

    public static boolean isConsole(CommandSender sender) {
        return (sender instanceof ConsoleCommandSender);
    }

    public static boolean isCommand(CommandSender sender) {
        return (sender instanceof CommandBlock);
    }


}


