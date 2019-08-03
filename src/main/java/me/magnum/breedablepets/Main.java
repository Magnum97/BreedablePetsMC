package me.magnum.breedablepets;

import co.aikar.commands.BukkitCommandManager;
import me.magnum.breedablepets.listeners.BreedListener;
import me.magnum.breedablepets.listeners.MyListener;
import me.magnum.breedablepets.util.Config;
import me.magnum.lib.Common;
import me.magnum.lib.SimpleConfig;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {
	
	
	public static Main plugin;
	public static SimpleConfig cfg;
	
	@Override
	public void onEnable () {
		plugin = this;
		Common.setInstance(plugin);
		Common.log("Loading breed-able pets...");
		cfg = new SimpleConfig("config.yml", plugin);
		Config.init();
		// Remain.setPlugin(plugin);  //todo Add compatability
		plugin.getServer().getPluginManager().registerEvents(new MyListener(), plugin);
		plugin.getServer().getPluginManager().registerEvents(new BreedListener(), plugin);
		registerCommands();
	}
	
	@SuppressWarnings("deprecation")
	private void registerCommands () {
		BukkitCommandManager commandManager = new BukkitCommandManager(plugin);
		commandManager.registerCommand(new me.magnum.breedablepets.Command());
		commandManager.enableUnstableAPI("help");
	}
	
	
	@Override
	public void onDisable () {
		Common.setInstance(plugin);
		Common.log("Disabling Breedable pets.");
		// Remain.setPlugin(null);
		cfg = null;
		plugin = null;
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
