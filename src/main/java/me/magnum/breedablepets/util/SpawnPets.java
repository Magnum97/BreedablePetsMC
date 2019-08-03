package me.magnum.breedablepets.util;

import me.magnum.breedablepets.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;

public class SpawnPets {
	
	public SpawnPets () {
	}
	
	public static void newParrot (Player player, Location location) {
		World world = player.getWorld();
		Parrot parrot = (Parrot) world.spawnEntity(location, EntityType.PARROT);
		parrot.setTamed(Main.cfg.getBoolean("hatches.tamed"));
		if (parrot.isTamed()) {
			parrot.setOwner(player);
			parrot.setSitting(true);
		}
		if (Main.cfg.getBoolean("hatches.named")) {
			parrot.setCustomNameVisible(true);
			parrot.setCustomName(ChatColor.translateAlternateColorCodes('&', Main.cfg.getString("hatches.name")));
		}
	}
}
