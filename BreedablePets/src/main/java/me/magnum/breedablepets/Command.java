package me.magnum.breedablepets;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.lib.CheckSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command extends BaseCommand {
	
	@CommandAlias("pegg")
	public void onCommand (CommandSender sender) {
		if (!CheckSender.isPlayer(sender)) {
			return;
		}
		ItemUtil util = new ItemUtil();
		Player p = (Player) sender;
		p.getInventory().addItem(util.egg).clone();
	}
	
}
