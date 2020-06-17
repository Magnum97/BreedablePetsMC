/*
 *
 *     Breedable PetsMC is a plugin to customize breeding of Minecraft mobs
 *     Copyright (C) 2019  Richard Simpson
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/gpl-3.0.html>
 *
 * Contact information:
 * Richard Simpson <magnum1997@gmail.com>
 * 19084 Leaf Lane
 * Redding, CA 96003
 * USA
 *
 */
package me.magnum.breedablepets;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.magnum.Breedable;
import me.magnum.breedablepets.util.CheckSender;
import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.breedablepets.util.SpawnPets;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// TODO refine permissions
// ie. breedable.command breedable.command.parrot breedable.command.reload
@CommandAlias("breedablepets|bp")
public class Command extends BaseCommand {
	
	@HelpCommand
	public void onHelp (CommandSender sender, CommandHelp help) {
		help.showHelp();
	}
	
	@Subcommand("parrotegg")
	@Description("Get a parrot egg")
	@CommandPermission("breedablepets.command.parrotegg")
	public void onCommand (CommandSender sender, @Default("false") String fertile) {
		ItemUtil util = new ItemUtil();
		Player p = (Player) sender;
		
		if (! CheckSender.isPlayer(sender)) {
			return;
		}
		if ((fertile.equalsIgnoreCase("fertile"))&& sender.hasPermission("breedablepets.parrot.fertile")) {
			p.getInventory().addItem(util.fertileEgg);
			return;
		}
		p.getInventory().addItem(util.regEgg).clone();
	}
	
	@Subcommand("reload")
	@CommandPermission("breedablepets.command.reload")
	public void onReload (CommandSender sender) {
		Breedable.getPlugin().getCfg().forceReload();
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
