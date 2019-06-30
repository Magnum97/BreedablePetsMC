package me.magnum.breedablepets.listeners;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import me.magnum.breedablepets.Main;
import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.lib.Common;
import me.magnum.lib.SimpleConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BreedListener implements Listener {
	
	private final ItemUtil items = new ItemUtil();
	private final SimpleConfig cfg = Main.cfg;
	
	public BreedListener () {
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFeed (PlayerInteractEntityEvent pie) {
		Player player = pie.getPlayer();
		Material hand = player.getInventory().getItemInMainHand().getType();
		
		Entity target = pie.getRightClicked();
		int configChance;
		if (pie.getHand() != EquipmentSlot.HAND) {
			return;
		}
		if (Arrays.asList(Material.BEETROOT_SEEDS,
		                  Material.SEEDS,
		                  Material.MELON_SEEDS,
		                  Material.PUMPKIN_SEEDS,
		                  Material.SPECKLED_MELON).contains(hand)) {
			configChance = foodCalc(target, hand);
		}
		else {
			return;
		}
		player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
		Common.tell(player, "Chance of egg: " + configChance);
		// if (!player.getUniqueId().toString().equalsIgnoreCase("f05b6506-c11e-4fec-bc36-f260a01f3fcf")) {
		// 	return;
		// }
		pie.setCancelled(true);
		// if (!pie.getPlayer().getInventory().getItemInMainHand().isSimilar(items.birdFood())) {
		// 	Common.tell(player, "You need bird food to feed the parrots.");
		// 	return;
		// }
		
		List <Entity> nearby = target.getNearbyEntities(12, 2, 12);
		
		World w = player.getWorld();
		Location loc = target.getLocation();
		boolean hasMate = false;
		
		for (Entity entity : nearby) {
			if (entity.getType() == target.getType()) {
				hasMate = true;
			}
		}
		int chance = new Random().nextInt(100);
		double x = target.getLocation().getX();
		double y = target.getLocation().getY() + 1;
		double z = target.getLocation().getZ();
		if (hasMate) {
			FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, target.getLocation(), 3);
			FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, x, y, z, 1);
			// FastParticle.spawnParticle(mateLoc.getWorld(), ParticleType.HEART, target.getLocation(), 3);
			// FastParticle.spawnParticle(w, ParticleType.HEART, x, y, z, 1);
			
			if (chance > cfg.getInt("fertile-chance")) {
				w.dropItemNaturally(loc, items.fertileEgg.clone());
			}
			else {
				w.dropItemNaturally(loc, items.regEgg.clone());
			}
		}
		else {
			if (chance > cfg.getInt("egg-chance")) {
				FastParticle.spawnParticle(w, ParticleType.NOTE, target.getLocation(), 3);
				FastParticle.spawnParticle(w, ParticleType.NOTE, x, y, z, 3);
				w.dropItemNaturally(loc, items.regEgg.clone());
			}
		}
		
	}
	
	// todo get length of configuration section and make array of item/chance pairs.
	private Integer foodCalc (Entity target, Material type) {
		int chance = 0;
		switch (type) {
			case SEEDS:
				chance = cfg.getInt("chance.wheat");
				break;
			case BEETROOT_SEEDS:
				chance = cfg.getInt("chance.beetroot");
			case PUMPKIN_SEEDS:
				chance = cfg.getInt("chance.pumpkin");
				break;
			case MELON_SEEDS:
				chance = cfg.getInt("chance.melon");
				break;
			case SPECKLED_MELON:
				chance = cfg.getInt("chance.glistering");
				break;
		}
		
		return chance;
		
	}
}

