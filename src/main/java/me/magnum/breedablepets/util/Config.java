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
package me.magnum.breedablepets.util;

import lombok.Getter;
import me.magnum.breedablepets.Main;
import me.magnum.lib.Common;
import me.magnum.lib.SimpleConfig;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Config extends SimpleConfig {
	
	// public static String version;
	public static int eggChance;
	public static int pairedChance;
	public static int hatchChance;
	public static int fertileChance;
	public static boolean nameHatchling;
	public static String hatchLingName;
	public static boolean tamedHatchling;
	
	private Config (String fileName, JavaPlugin plugin) {
		super(fileName, plugin);
		setHeader(new String[] {
				"--------------------------------------------------------",
				" Your configuration file got updated automatically!",
				" ",
				" Unfortunately, due to how Bukkit saves .yml files,",
				" all comments in your file were lost. Please open",
				" " + fileName + " from jar to browse the default values.",
				"--------------------------------------------------------"
		});
	}
	
	public static void init () {
		new Config("config.yml", Main.plugin).onLoad();
	}
	
	private void onLoad () {
		Common.setInstance(Main.plugin);
		// version = getString("version");
		eggChance = getInt("egg-chance");
		pairedChance = getInt("paired-egg-chance");
		fertileChance = getInt("fertile-egg-chance");
		nameHatchling = getBoolean("hatches.named");
		hatchLingName = getString("hatches.name");
		tamedHatchling = getBoolean("hatches.tamed");
		
	}
	
}
