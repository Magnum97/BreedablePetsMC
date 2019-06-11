package me.magnum.breedablepets.util;

import org.bukkit.Location;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;

public class SpawnPets {
	
	public SpawnPets () {
	}
	
	public void newParrot (Player player, Location location) {
		Parrot newParrot = player.getWorld().spawn(location, Parrot.class);
		newParrot.setTamed(true);
		newParrot.setOwner(player);
		newParrot.setSitting(true);
		newParrot.setNoDamageTicks(1000);
	}
}
