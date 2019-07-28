package me.magnum.breedablepets.listeners;

import me.magnum.breedablepets.Main;
import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.breedablepets.util.SpawnPets;
import me.magnum.lib.Common;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;

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
}
