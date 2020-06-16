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
import lombok.SneakyThrows;
import me.magnum.breedablepets.Command;
import me.magnum.breedablepets.listeners.BreedListener;
import me.magnum.breedablepets.listeners.EggListener;
import me.magnum.breedablepets.util.Common;
import me.magnum.breedablepets.util.DataWorks;
import me.magnum.breedablepets.util.InvalidMaterialException;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class Breedable extends JavaPlugin {

	@Getter
	private static Breedable plugin;
	@Getter
	Logger log;
	@Getter
	private Yaml cfg;
	@Getter
	private String pre;

	@SneakyThrows
	@Override
	public void onEnable () {
		plugin = this;
		log = plugin.getLogger();
		pre = "&7[&6" + plugin.getName() + "&7] ";
		pre = ChatColor.translateAlternateColorCodes('&', pre);
//		Remain.setPlugin(plugin);  // TODO Add backwards compatibility
		plugin.getServer().getPluginManager().registerEvents(new EggListener(), this);
		plugin.getServer().getPluginManager().registerEvents(new BreedListener(), this);
		registerCommands();
		setupConfig();
	}

	private void setupConfig () throws InvalidMaterialException {
		cfg = new Yaml("config.yml", getDataFolder().toString(), getResource("config.yml"));
		String[] header = {"Config file for BreedablePets",
				"by Magnum1997",
				"This should be self-explanatory. I tried to comment settings.",
				"For problems, questions, or feedback please visit",
				"https://github.com/Magnum97/BreedablePetsMC/issues"};
		cfg.framedHeader(header);
		boolean valid;
		valid = materialsAreValid();
		if (! valid) {
			log.warning("Invalid materials in config.yml");
			log.severe("Valid Material names can be found at " +
					"https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html");
			log.severe("Plugin is disabled");
			plugin.setEnabled(false);
		}
	}

	private boolean materialsAreValid () throws InvalidMaterialException {
		FlatFileSection section = Breedable.getPlugin().getCfg().getSection("parrot.egg-lay.modifiers");

		boolean isValid;

		for (String material : section.keySet()) {
			log.info("Setting " + material + " modifier to " + section.getInt(material));
			try {
				Material.valueOf(material);
			}
			catch (IllegalArgumentException e) {
				throw new InvalidMaterialException(material, "Invalid Material in config.yml", e);
			}
		}
		isValid = true;
		return isValid;
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
		cfg.write();
		DataWorks dw = new DataWorks();
		dw.save();
		cfg = null;
		plugin = null;
	}

	public void spawnMob (World world, Location location, EntityType mobType) {
		world.spawnEntity(location, mobType);
	}
}
