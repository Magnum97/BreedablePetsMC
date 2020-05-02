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
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class MyListener implements Listener {

	private final ItemUtil itemsAPI = new ItemUtil();

	@EventHandler (priority = EventPriority.HIGH)
	public void onEggThrow (PlayerEggThrowEvent e) {
		if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(itemsAPI.regEgg)) {
			e.setHatching(false);
			Common.sendBar(e.getPlayer(), "I really hope it hatches...");
			if (ThreadLocalRandom.current().nextInt(100) < Breedable.getCfg().getInt("egg-change")) ;
			{
				SpawnPets.newParrot(e.getPlayer(), e.getEgg().getLocation());
			}
		}
		if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(itemsAPI.fertileEgg)) {
			e.setHatching(false);
			Common.sendBar(e.getPlayer(), "You try hatching the Parrot egg...");
			SpawnPets.newParrot(e.getPlayer(), e.getEgg().getLocation());
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
		// todo add config option here
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
