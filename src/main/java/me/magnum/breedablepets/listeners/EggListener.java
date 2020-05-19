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

import me.magnum.Breedable;
import me.magnum.breedablepets.util.Common;
import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.breedablepets.util.SpawnPets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class EggListener implements Listener {

	private static final HashMap <UUID, Player> flyingEggs = new HashMap <>();
	private static final HashMap <UUID, Player> flyingFertileEggs = new HashMap <>();
	private final ItemUtil itemsAPI = new ItemUtil();

	@EventHandler (priority = EventPriority.HIGH)
	public void onEggThrow (PlayerEggThrowEvent e) {
		ItemStack hand = e.getPlayer().getInventory().getItemInMainHand();
		if (! hand.isSimilar(itemsAPI.regEgg) || hand.isSimilar(itemsAPI.fertileEgg)) return;

		UUID eggId = e.getEgg().getUniqueId();
		e.setHatching(false);
		if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(itemsAPI.regEgg)) {
			Common.sendBar(e.getPlayer(), "I really hope it hatches...");
			flyingEggs.put(eggId, e.getPlayer());
		}
		if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(itemsAPI.fertileEgg)) {
			flyingFertileEggs.put(eggId, e.getPlayer());
			Common.sendBar(e.getPlayer(), "You try hatching the Parrot egg...");
		}
	}

	@EventHandler
	public void eggHit (ProjectileHitEvent hitEvent) {
		UUID projectile = hitEvent.getEntity().getUniqueId();
		Location loc = hitEvent.getEntity().getLocation();
		if (flyingEggs.containsKey(projectile)) {
			// handle regular egg
			int random = ThreadLocalRandom.current().nextInt(100);
			int chance = (int) Breedable.getPlugin().getCfg().get("hatch-chance");
			Breedable.getPlugin().getServer().broadcastMessage("Random: " + random + " Chance: " + chance);
			if (random < chance) {
				SpawnPets.newParrot(flyingEggs.get(projectile), loc);
			}
			flyingEggs.remove(projectile);
		}
		if (flyingFertileEggs.containsKey(projectile)) {
			// handle fertile egg
			SpawnPets.newParrot(flyingFertileEggs.get(projectile), loc);
			flyingFertileEggs.remove(projectile);
		}
	}
/*
	@EventHandler
	public void onHatch (CreatureSpawnEvent e) {
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.DISPENSE_EGG)) {
			e.setCancelled(true);
		}
	}
*/

	@EventHandler
	public void onDispenseEgg (BlockDispenseEvent e) {
		// TODO add config option here
		if ((e.getItem() != null) && (e.getItem().getType() != Material.AIR)) {
			if (e.getItem().getType() == Material.EGG) {
				Dispenser dispenser = (Dispenser) e.getBlock().getState();
				if (dispenser.getInventory().contains(Material.EGG)) {
					ItemStack before;
					int amount;
					int slot;
					slot = dispenser.getInventory().first(Material.EGG);
					before = dispenser.getInventory().getItem(slot);
					amount = before.getAmount();
					before.setAmount(amount - 1);
					e.setCancelled(true);
				}
			}
		}
	}
}
