package me.magnum.breedablepets.listeners;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import me.magnum.breedablepets.Main;
import me.magnum.breedablepets.util.Config;
import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.lib.SimpleConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BreedListener implements Listener {
	
	private final ItemUtil items = new ItemUtil();
	private final SimpleConfig cfg = Main.cfg;
	
	public BreedListener () {
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFeed (PlayerInteractEntityEvent pie) {
		Player player = pie.getPlayer();
		Entity target = pie.getRightClicked();
		Material hand = player.getInventory().getItemInMainHand().getType();
		if (target.getType() != EntityType.PARROT) {
			return;
		}
		Tameable tamed = (Tameable) target;
		Sittable sitting = (Sittable) target;
		if ((!tamed.isTamed()) || (!sitting.isSitting())) {
			return;
		}
		int chanceModifier;
		if (pie.getHand() != EquipmentSlot.HAND) {
			return;
		}
		if (Arrays.asList(Material.BEETROOT_SEEDS,
		                  Material.MELON_SEEDS,
		                  Material.MELON,
		                  Material.PUMPKIN_SEEDS,
		                  Material.SPECKLED_MELON).contains(hand)) {
			chanceModifier = foodCalc(target, hand); // todo increase chance of egg/fertile egg by type of food.
		}
		else {
			return;
		}
		pie.setCancelled(true);
		player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
		
		// Common.tell(player, "Base chance of egg: " + chanceModifier); // todo remove before deploy
		
		List <Entity> nearby = target.getNearbyEntities(5, 2, 5);
		
		World w = player.getWorld();
		Location loc = target.getLocation();
		Entity mate = null;
		boolean hasMate = false;
		
		for (Entity entity : nearby) {
			if (entity.getType() == target.getType()) {
				hasMate = true;
				mate = entity;
			}
		}
		
/* todo find nearest parrot
		for (int i = 0; i < nearby.size(); i++) {
			if (nearby.get(i).getType().equals(EntityType.PARROT)){
				hasMate=true;
				mate=nearby.get(i);
				break;
			}
		}
*/
		
		// int random = new Random().nextInt(100);
		double x = target.getLocation().getX();
		double y = target.getLocation().getY() + 1;
		double z = target.getLocation().getZ();
		if (hasMate) {
			if (ThreadLocalRandom.current().nextInt(100) < Config.fertileChance ) {
				w.dropItemNaturally(loc, items.fertileEgg.clone());
				FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, target.getLocation(), 3);
				FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, x, y, z, 3);
				FastParticle.spawnParticle(w, ParticleType.HEART, mate.getLocation(), 3);
				FastParticle.spawnParticle(w, ParticleType.HEART, x, y, z, 3);
			}
			else {
				if (ThreadLocalRandom.current().nextInt(1, 101) < Config.eggChance ) {
					w.dropItemNaturally(loc, items.regEgg.clone());
					FastParticle.spawnParticle(target.getWorld(), ParticleType.REDSTONE, target.getLocation(), 3, x, y, z);
					FastParticle.spawnParticle(mate.getWorld(), ParticleType.REDSTONE, mate.getLocation(), 1, x, y, z);
				}
			}
			
		}
		else {
			if (ThreadLocalRandom.current().nextInt(100) < Config.eggChance) {
				FastParticle.spawnParticle(w, ParticleType.NOTE, target.getLocation(), 3, x, y, z);
				// FastParticle.spawnParticle(w, ParticleType.NOTE, x, y, z, 1);
				w.dropItemNaturally(loc, items.regEgg.clone());
			}
		}
		
	}
	
	// todo get length of configuration section and make array of item/chance pairs.
	private Integer foodCalc (Entity target, Material type) {
		int chance = 0;
		switch (type) {
			case SEEDS:
				chance = cfg.getInt("modifier.wheat");
				break;
			case BEETROOT_SEEDS:
				chance = cfg.getInt("modifier.beetroot");
				break;
			case PUMPKIN_SEEDS:
				chance = cfg.getInt("modifier.pumpkin");
				break;
			case MELON_SEEDS:
				chance = cfg.getInt("modifier.melon");
				break;
			case SPECKLED_MELON:
				chance = cfg.getInt("modifier.glistering");
				break;
		}
		return chance;
	}
}

