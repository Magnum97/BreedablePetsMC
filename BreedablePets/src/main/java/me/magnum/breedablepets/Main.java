package me.magnum.breedablepets;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.mineacademy.remain.Remain;

public class Main extends JavaPlugin {
	
	@Getter public Main plugin;
	@Getter public SimpleConf
	@Override
	public void onEnable(){
		plugin = this;
		Remain.setPlugin(plugin);
		
	}
	
	
}
