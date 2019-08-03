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
	
	public static void init () {
		new Config("config.yml", Main.plugin).onLoad();
	}
	
}
