package me.magnum.breedablepets;

import co.aikar.commands.BukkitCommandManager;
import me.magnum.breedablepets.listeners.MyListener;
import me.magnum.lib.Common;
import me.magnum.lib.SimpleConfig;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;
import org.mineacademy.remain.Remain;


public class Main extends JavaPlugin {
	
	
	public Main plugin;
	
	public SimpleConfig cfg;
	private BukkitCommandManager commandManager;
	
	@Override
	public void onEnable () {
		plugin = this;
		cfg = new SimpleConfig("config.yml", plugin);
		Remain.setPlugin(plugin);
		Common.setInstance(plugin);
		Common.log("Loading breedable pets...");
		plugin.getServer().getPluginManager().registerEvents(new MyListener(), plugin);
		registerCommands();
	}
	
	private void registerCommands () {
		commandManager = new BukkitCommandManager(plugin);
		commandManager.registerCommand(new me.magnum.breedablepets.Command());
	}
	
	
	@Override
	public void onDisable () {
		Common.setInstance(plugin);
		Remain.setPlugin(null);
		Common.log("Disabling Breedable pets.");
	}
	
	public Main getPlugin () {
		return plugin;
	}
	
	public SimpleConfig getCfg () {
		return cfg;
	}
	
	public void spawnMob (World world, Location location, EntityType mobType) {
		world.spawnEntity(location, mobType);
	}
}
