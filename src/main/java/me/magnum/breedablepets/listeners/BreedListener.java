/*
 *
 *     Breedable PetsMC is a plugin to customize breeding of Minecraft mobs
 *     Copyright (C) 2019  Richard Simpson
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/gpl-3.0.html>
 *
 * Contact information:
 * Richard Simpson <magnum1997@gmail.com>
 * 19084 Leaf Lane
 * Redding, CA 96003
 * USA
 *
 */
package me.magnum.breedablepets.listeners;

import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import me.magnum.Breedable;
import me.magnum.breedablepets.util.ItemUtil;
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
	//	private final SimpleConfig cfg = Main.getCfg();

	public BreedListener () {
	}

	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFeed (PlayerInteractEntityEvent pie) {
		Player player = pie.getPlayer();
		Entity target = pie.getRightClicked();
		Material hand = player.getInventory().getItemInMainHand().getType();
		if (target.getType() != EntityType.PARROT) {
			return;
		}
		Tameable tamed = (Tameable) target;
		Sittable sitting = (Sittable) target;
		if ((! tamed.isTamed()) || (! sitting.isSitting())) {
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
				Material.GLISTERING_MELON_SLICE).contains(hand)) {
			chanceModifier = foodCalc(target, hand); // TODO increase chance of egg/fertile egg by type of food.
		}
		else {
			return;
		}
		pie.setCancelled(true);
		sitting.setSitting(true);
		player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

		// Common.tell(player, "Base chance of egg: " + chanceModifier); // TODO remove before deploy

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
		
/* // TODO find nearest parrot
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
			if (ThreadLocalRandom.current().nextInt(100) < (Breedable.getCfg().getInt("fertile-egg-chance"))) {
				w.dropItemNaturally(loc, items.regEgg.clone());
				// w.dropItemNaturally(loc, items.fertileEgg.clone());  // TODO To be fertile egg - disabled until single throw bug fixed
				FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, target.getLocation(), 3);
				FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, x, y, z, 3);
				FastParticle.spawnParticle(w, ParticleType.HEART, mate.getLocation(), 3);
				FastParticle.spawnParticle(w, ParticleType.HEART, x, y, z, 3);
			}
			else {
				if (ThreadLocalRandom.current().nextInt(1, 101) < Breedable.getCfg().getInt("egg-chance")) {
					w.dropItemNaturally(loc, items.regEgg.clone());
					FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, target.getLocation(), 3);
					FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, x, y, z, 3);
					FastParticle.spawnParticle(w, ParticleType.HEART, mate.getLocation(), 3);
					FastParticle.spawnParticle(w, ParticleType.HEART, x, y, z, 3);
				}
			}

		}
		else {
			if (ThreadLocalRandom.current().nextInt(100) < Breedable.getCfg().getInt("egg-change")) {
				FastParticle.spawnParticle(w, ParticleType.NOTE, target.getLocation(), 3, x, y, z);
				// FastParticle.spawnParticle(w, ParticleType.NOTE, x, y, z, 1);
				w.dropItemNaturally(loc, items.regEgg.clone());
			}
		}

	}

	// TODO get length of configuration section and make array of item/chance pairs.
	private Integer foodCalc (Entity target, Material type) {
		int chance = 0;
		switch (type) {
			case WHEAT_SEEDS:
				chance = Breedable.getCfg().getInt("modifier.wheat");
				break;
			case BEETROOT_SEEDS:
				chance = Breedable.getCfg().getInt("modifier.beetroot");
				break;
			case PUMPKIN_SEEDS:
				chance = Breedable.getCfg().getInt("modifier.pumpkin");
				break;
			case MELON_SEEDS:
				chance = Breedable.getCfg().getInt("modifier.melon");
				break;
			case GLISTERING_MELON_SLICE:
				_MELON:
				chance = Breedable.getCfg().getInt("modifier.glistering");
				break;
		}
		return chance;
	}
}

