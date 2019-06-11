package me.magnum.petmanager.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CatchUnknown;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.magnum.lib.Common;
import me.magnum.petmanager.menus.UIMenu;
import me.magnum.petmanager.util.CheckSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.magnum.petmanager.PetManager.pre;

@CommandAlias("petman")
public class MenuCommand extends BaseCommand {

    public MenuCommand() {
    }

    @Default
    @CatchUnknown
    protected void onPMn(CommandSender sender) {
        if (CheckSender.isCommand(sender)) {
            return;
        }
        Common.tell(sender, pre + "&aTest successful.");
        if (CheckSender.isPlayer(sender)) {
            Player p = (Player) sender;
            new UIMenu().displayTo(p);
        }
    }
}




