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

import de.leonhard.storage.Yaml;
import fr.mrmicky.fastparticle.FastParticle;
import fr.mrmicky.fastparticle.ParticleType;
import lombok.SneakyThrows;
import me.magnum.Breedable;
import me.magnum.breedablepets.util.Common;
import me.magnum.breedablepets.util.DataWorks;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class BreedListener implements Listener {

	private final ItemUtil items = new ItemUtil();
	private final Breedable plugin = Breedable.getPlugin();
	private final Yaml cfg = plugin.getCfg();
	private final String pre = plugin.getPre();
	private final HashSet <UUID> onCoolDown = new HashSet <>();

	public BreedListener () {
	}

	@SneakyThrows
	@EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onFeed (PlayerInteractEntityEvent pie) {
		Player player = pie.getPlayer();
		Entity target = pie.getRightClicked();
		Material hand = player.getInventory().getItemInMainHand().getType();
		HashMap <Material, Integer> map;
		DataWorks dataWorks = new DataWorks();
		map = dataWorks.getMaterialsMap();
		if (target.getType() != EntityType.PARROT) {
			return;
		}
		if (onCoolDown.contains(target.getUniqueId())) {
			Common.tell(player, pre + "This parrot needs rest before it can lay another egg.");
			pie.setCancelled(true);
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
		if (map.containsKey(hand)) {
			chanceModifier = map.get(hand);
		}
		else
			return;
		pie.setCancelled(true);
		sitting.setSitting(true);
		player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
		int baseChance = Breedable.getPlugin().getCfg().getInt("parrot.egg-lay.base-chance");
		int matedChance = Breedable.getPlugin().getCfg().getInt("parrot.egg-lay.mated-chance");
		int finalChance = baseChance + chanceModifier;

		////////////////////////  DEBUG CODE ////////////////////////////
		Logger log = plugin.getLogger();
		log.info("Base chance of egg : " + baseChance);
		log.info("Food modifier      : " + chanceModifier);
		log.info("Final chance       : " + finalChance);
		log.info("Fertile chance     : " + matedChance);
		/////////////////////////////////////////////////////////////////

		List <Entity> nearby = target.getNearbyEntities(5, 2, 5);

		World w = player.getWorld();
		Location loc = target.getLocation();
		Entity mate = null;
		boolean hasMate = false;

		for (Entity entity : nearby) {
			if (entity.getType() == target.getType()) {
				Tameable thisMate = (Tameable) entity;
				if ((thisMate.isTamed()) &&
						(Objects.equals(thisMate.getOwner(), (tamed.getOwner()))) &&
						(! onCoolDown.contains(thisMate.getUniqueId()))) {
					hasMate = true;
					mate = entity;
				doCoolDown(mate.getUniqueId());
					break;
				}
			}
		}
		double x = target.getLocation().getX();
		double y = target.getLocation().getY() + 1;
		double z = target.getLocation().getZ();
		doCoolDown(target.getUniqueId());
		int random = ThreadLocalRandom.current().nextInt(100);
		if (hasMate) {
			if (random < matedChance + chanceModifier) {
				int r2 = ThreadLocalRandom.current().nextInt(100);
				boolean fertile = r2 < Breedable.getPlugin().getCfg().getInt("parrot.egg-lay.fertile-chance:");

				if (fertile)
					w.dropItemNaturally(loc, items.fertileEgg.clone());
				else
					w.dropItemNaturally(loc, items.regEgg.clone());

				FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, target.getLocation(), 3);
				FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, x, y, z, 3);
				FastParticle.spawnParticle(w, ParticleType.HEART, mate.getLocation(), 3);
				FastParticle.spawnParticle(w, ParticleType.HEART, x, y, z, 3);
			}
			else {
				if (random < finalChance) {
					w.dropItemNaturally(loc, items.regEgg.clone());
					FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, target.getLocation(), 3);
					FastParticle.spawnParticle(target.getWorld(), ParticleType.HEART, x, y, z, 3);
					FastParticle.spawnParticle(w, ParticleType.HEART, mate.getLocation(), 3);
					FastParticle.spawnParticle(w, ParticleType.HEART, x, y, z, 3);
				}
			}

		}
		else {
			if (ThreadLocalRandom.current().nextInt(100) < Breedable.getPlugin().getCfg().getInt("parrot.egg-lay.base-chance")) {
				FastParticle.spawnParticle(w, ParticleType.NOTE, target.getLocation(), 3, x, y, z);
				// FastParticle.spawnParticle(w, ParticleType.NOTE, x, y, z, 1);
				w.dropItemNaturally(loc, items.regEgg.clone());
			}
		}

	}

	private void doCoolDown (UUID uniqueId) {
		onCoolDown.add(uniqueId);
		BukkitRunnable cooldown = new BukkitRunnable() {
			@Override
			public void run () {
				onCoolDown.remove(uniqueId);
			}
		};
		cooldown.runTaskLater(plugin, 20 * Breedable.getPlugin().getCfg().getOrSetDefault("cooldown.parrot", 15));
	}

	// TODO get length of configuration section and make array of item/chance pairs.
	private Integer foodCalc (Entity target, Material type) {
		int chance = 0;
		switch (type) {
			case WHEAT_SEEDS:
				chance = Breedable.getPlugin().getCfg().getInt("modifier.wheat");
				break;
			case BEETROOT_SEEDS:
				chance = Breedable.getPlugin().getCfg().getInt("modifier.beetroot");
				break;
			case PUMPKIN_SEEDS:
				chance = Breedable.getPlugin().getCfg().getInt("modifier.pumpkin");
				break;
			case MELON_SEEDS:
				chance = Breedable.getPlugin().getCfg().getInt("modifier.melon");
				break;
			case GLISTERING_MELON_SLICE:
				_MELON:
				chance = Breedable.getPlugin().getCfg().getInt("modifier.glistering");
				break;
		}
		return chance;
	}
}

