package me.magnum.breedablepets.listeners;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.lib.Common;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.List;
import java.util.Random;

public class BreedListener implements Listener {
	
	private ItemUtil items = new ItemUtil();
	
	public BreedListener () {
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFeed (PlayerInteractEntityEvent pie) {
		Player player = pie.getPlayer();
		Material hand = player.getInventory().getItemInMainHand().getType();
		Material tool = Material.PAPER;
		Entity target = pie.getRightClicked();
		if (pie.getHand() != EquipmentSlot.HAND) {
			return;
		}
		if (hand != tool) {
			return;
		}
		player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
		if (!player.getUniqueId().toString().equalsIgnoreCase("f05b6506-c11e-4fec-bc36-f260a01f3fcf")) {
			return;
		}
		if (!pie.getRightClicked().getType().equals(EntityType.PARROT)) {
			Common.tell(player, "This is not a parrot");
			return;
		}
		pie.setCancelled(true);
		// if (!pie.getPlayer().getInventory().getItemInMainHand().isSimilar(items.birdFood())) {
		// 	Common.tell(player, "You need bird food to feed the parrots.");
		// 	return;
		// }
		
		List <Entity> nearby = target.getNearbyEntities(12, 2, 12);
		
		World w = player.getWorld();
		Location loc = target.getLocation();
		Entity mate;
		boolean hasMate = false;
		boolean fertile = false;
		
		for (Entity entity : nearby) {
			if (entity.getType() == target.getType()) {
				hasMate = true;
			}
		}
		int chance = new Random().nextInt(100);
		double x = target.getLocation().getX();
		double y = target.getLocation().getY() + 0.5;
		double z = target.getLocation().getZ();
		if (hasMate) {
			FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, target.getLocation(), 3);
			FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, x, y, z, 1);
			// FastParticle.spawnParticle(mateLoc.getWorld(), ParticleType.HEART, target.getLocation(), 3);
			// FastParticle.spawnParticle(w, ParticleType.HEART, x, y, z, 1);
			
			if (chance > 70) { // todo move chance to config
				w.dropItemNaturally(loc, items.fertileEgg.clone());
			}
			else {
				w.dropItemNaturally(loc, items.regEgg.clone());
			}
		}
		else {
			if (chance > 70) { //todo move to config
				FastParticle.spawnParticle(target.getWorld(), ParticleType.VILLAGER_HAPPY, loc, 5, Color.GREEN);
				FastParticle.spawnParticle(target.getWorld(), ParticleType.VILLAGER_HAPPY, loc, 5, Color.GREEN);
				w.dropItemNaturally(loc, items.regEgg.clone());
			}
		}
		
	}
}

