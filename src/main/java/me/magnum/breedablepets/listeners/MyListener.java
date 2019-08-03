package me.magnum.breedablepets.listeners;

import me.magnum.breedablepets.Main;
import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.breedablepets.util.SpawnPets;
import me.magnum.lib.Common;
import me.magnum.lib.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class MyListener implements Listener {
	
	private ItemUtil itemsAPI = new ItemUtil();
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEggThrow (PlayerEggThrowEvent e) {
		if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(itemsAPI.regEgg)) {
			e.setHatching(false);
			Common.sendABar(e.getPlayer(), "I really hope it hatches...");
			if (ThreadLocalRandom.current().nextInt(100) < Main.cfg.getInt("hatch-chance")) {
				SpawnPets.newParrot(e.getPlayer(), e.getEgg().getLocation());
			}
		}
		if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(itemsAPI.fertileEgg)) {
			e.setHatching(false);
			Common.sendABar(e.getPlayer(), "You try hatching the Parrot egg...");
			SpawnPets.newParrot(e.getPlayer(), e.getEgg().getLocation());
		}
	}
	
	@EventHandler
	public void onHatch (CreatureSpawnEvent e) {
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.DISPENSE_EGG)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onDispenseEgg (BlockDispenseEvent e) {
		// todo add config option here
		Common.setInstance(Main.plugin);
		if ((e.getItem() != null) && (e.getItem().getType() != Material.AIR)) {
			if (e.getItem().getType() == Material.EGG){
				Dispenser dispensoer = (Dispenser)e.getBlock().getState();
				if (dispensoer.getInventory().contains(Material.EGG)){
					ItemStack before;
					int amount;
					int slot;
					slot = dispensoer.getInventory().first(Material.EGG);
					before = dispensoer.getInventory().getItem(slot);
					amount = before.getAmount();
					before.setAmount(amount-1);
				e.setCancelled(true);
				}
			}
		}
	}
}
