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
package me.magnum;

import co.aikar.commands.BukkitCommandManager;
import de.leonhard.storage.Yaml;
import de.leonhard.storage.sections.FlatFileSection;
import lombok.Getter;
import me.magnum.breedablepets.Command;
import me.magnum.breedablepets.listeners.BreedListener;
import me.magnum.breedablepets.listeners.EggListener;
import me.magnum.breedablepets.util.Common;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

public class Breedable extends JavaPlugin {

	@Getter
	private static Breedable plugin;
	//	@Getter
//	private SimpleConfig cfg;
	@Getter
	private Yaml cfg;
	@Getter
	private String pre;

	@Override
	public void onEnable () {
		plugin = this;
		setupConfig();
		pre = "&7[&6" + plugin.getName() + "&7] ";
		pre = ChatColor.translateAlternateColorCodes('&', pre);
//		cfg.getString("prefix");
//		Remain.setPlugin(plugin);  // TODO Add backwards compatibility
		plugin.getServer().getPluginManager().registerEvents(new EggListener(), this);
		plugin.getServer().getPluginManager().registerEvents(new BreedListener(), this);
		registerCommands();
	}

	private void setupConfig () {
		cfg = new Yaml("settings.yml", getDataFolder().toString(), getResource("settings.yml"));
		String[] header = {"Config file for BreedablePets",
				"by Magnum1997",
				"This should be self-explanatory. I tried to comment settings.",
				"For problems, questions, or feedback please visit",
				"https://github.com/Magnum97/BreedablePetsMC/issues"};
		cfg.framedHeader(header);
		// Set defaults
		cfg.setDefault("parrots.hatch-chance", 10);
		cfg.setDefault("parrot.egg-chance", 25);
		cfg.setDefault("paired-egg-chance", 20);
		cfg.setDefault("fertile-egg-chance", 30);
		cfg.setDefault("hatch-chance", 10);
		cfg.setDefault("rare-chance", 1);
		FlatFileSection modifiers = cfg.getSection("modifiers");
		modifiers.setDefault("wheat", 10);
		modifiers.setDefault("beetroot", 20);
		modifiers.setDefault("pumpkin", 30);
		modifiers.setDefault("melon", 30);
		modifiers.setDefault("glistering-melon", 100);
	}

	@SuppressWarnings ("deprecation")
	private void registerCommands () {
		BukkitCommandManager commandManager = new BukkitCommandManager(plugin);
		commandManager.registerCommand(new Command());
		commandManager.enableUnstableAPI("help");
	}


	@Override
	public void onDisable () {
		Common.log("Disabling Breed-able pets.");
		// Remain.setPlugin(null);
		//		cfg.saveConfig();
		cfg = null;
		plugin = null;
	}

	public void spawnMob (World world, Location location, EntityType mobType) {
		world.spawnEntity(location, mobType);
	}
}
