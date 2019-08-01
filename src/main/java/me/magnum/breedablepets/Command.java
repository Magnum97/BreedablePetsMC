package me.magnum.breedablepets;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.breedablepets.util.SpawnPets;
import me.magnum.lib.CheckSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("breedablepet|bp")
public class Command extends BaseCommand {
	
	@HelpCommand
	public void onHelp (CommandSender sender, CommandHelp help) {
		help.showHelp();
	}
	
	@Subcommand("parrotegg")
	@Description("Get a parrot egg")
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
	
	@Subcommand("reload")
	public void onReload (CommandSender sender) {
		Main.cfg.reloadConfig();
	}
	
	@CommandAlias("sp")
	@CommandPermission("me.magnum")
	public void onCommand (CommandSender sender) {
		if (!CheckSender.isPlayer(sender)) {
			return;
		}
		Player p = (Player) sender;
		SpawnPets.newParrot(p, p.getLocation());
	}
	
	
}
