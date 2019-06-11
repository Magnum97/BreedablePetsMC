package me.magnum.petmanager.commands;


import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.magnum.lib.Common;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

public abstract class PlayerCommand extends Command {

    private Player player;
    private String[] args;

    @Setter(value=AccessLevel.PROTECTED)
    private String prefix;

    protected PlayerCommand(String name) {
        super(name);
    }

    @Override
    public final boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            Common.tell(sender, "&cYou must be a player to run this command.");

            return false;
        }

        final Player player = (Player) sender;
        this.player = player;
        this.args = args;

        try {
            run(player, args);
        } catch (final ReturnedCommandException ex) {
            final String tellMessage = ex.tellMessage;

            tell(tellMessage);
        }

        return false;
    }

    protected abstract void run(Player player, String[] args);

    protected void checkBoolean(boolean toCheck, String falseMessage) {
        if (!toCheck)
            returnTell(falseMessage);
    }

    protected void checkNotNull(Object toCheck, String nullMessage) {
        if (toCheck == null)
            returnTell(nullMessage);
    }

    protected void checkArgsStrict(int requiredLength, String message) {
        if (args.length != requiredLength)
            returnTell(message);
    }

    protected int getNumber(int argsIndex, int from, int to, String errorMessage) {
        int age = 0;

        try {
            age = Integer.parseInt(args[argsIndex]);
            Validate.isTrue(age >= from && age <= to);

        } catch (final IllegalArgumentException e) {
            returnTell(errorMessage.replace("{min}", from + "").replace("{max}", to + ""));
        }

        return age;
    }

    protected void returnTell(String message) {
        throw new ReturnedCommandException(message);
    }

    protected void tell(String message) {
        Common.tell(player, (prefix != null ? "&8[&7" + prefix + "&8] " : "") + "&7" + message);
    }

    @RequiredArgsConstructor
    private final class ReturnedCommandException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        private final String tellMessage;
    }
}
