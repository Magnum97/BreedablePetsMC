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

import co.aikar.commands.BukkitCommandManager;
import lombok.Getter;
import me.magnum.breedablepets.listeners.BreedListener;
import me.magnum.breedablepets.listeners.MyListener;
import me.magnum.breedablepets.util.Common;
import me.magnum.breedablepets.util.SimpleConfig;
import me.magnum.breedablepets.util.SimpleConfigManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

@kr.entree.spigradle.Plugin
public class Main extends JavaPlugin {

	@Getter
	private static Main plugin;
	@Getter
	private static SimpleConfig cfg;
	@Getter
	private SimpleConfigManager configManager;

	@Override
	public void onEnable () {
		plugin = this;
		configManager = new SimpleConfigManager(plugin);
		Common.log("Loading breed-able pets...");
		cfg = configManager.getNewConfig("config.yml");
		//		cfg = new SimpleConfig("config.yml", plugin);
		// Remain.setPlugin(plugin);  //todo Add compatability
		plugin.getServer().getPluginManager().registerEvents(new MyListener(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new BreedListener(), plugin);
		registerCommands();
	}

	@SuppressWarnings ("deprecation")
	private void registerCommands () {
		BukkitCommandManager commandManager = new BukkitCommandManager(plugin);
		commandManager.registerCommand(new me.magnum.breedablepets.Command());
		commandManager.enableUnstableAPI("help");
	}


	@Override
	public void onDisable () {
		Common.log("Disabling Breedable pets.");
		// Remain.setPlugin(null);
		cfg = null;
		plugin = null;
	}

	public void spawnMob (World world, Location location, EntityType mobType) {
		world.spawnEntity(location, mobType);
	}
}
