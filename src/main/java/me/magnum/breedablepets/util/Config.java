package me.magnum.breedablepets.util;

import lombok.Getter;
import me.magnum.breedablepets.Main;
import me.magnum.lib.Common;
import me.magnum.lib.SimpleConfig;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Config extends SimpleConfig {
	
	
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
	}
	
	public static void init () {
		new Config("config.yml", Main.plugin).onLoad();
	}
	
}
