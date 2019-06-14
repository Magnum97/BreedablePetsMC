package me.magnum.breedablepets;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.lib.CheckSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command extends BaseCommand {
	
	@CommandAlias("parrotegg")
	@CommandPermission("breedable.parrot.regEgg")
	public void onCommand (CommandSender sender, @Default("false") String fertile) {
		ItemUtil util = new ItemUtil();
		Player p = (Player) sender;
		
		if (!CheckSender.isPlayer(sender)) {
			return;
		}
		if (fertile.equalsIgnoreCase("fertile")) {
			p.getInventory().addItem(util.fertileEgg);
			return;
		}
		p.getInventory().addItem(util.regEgg).clone();
	}
	
	
}
