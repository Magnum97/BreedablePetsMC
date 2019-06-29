package me.magnum.breedablepets.listeners;

import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.breedablepets.util.SpawnPets;
import me.magnum.lib.Common;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class MyListener implements Listener {
	
	private ItemUtil itemsAPI = new ItemUtil();
	private SpawnPets spawner = new SpawnPets();
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onEggThrow (PlayerEggThrowEvent e) {
		if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(itemsAPI.regEgg)) {
			e.setHatchingType(EntityType.PARROT);
			e.setNumHatches((byte) 1);
			// todo make hatched parrots tamed
			Common.sendABar(e.getPlayer(), "I really hope it hatches...");
		}
		if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(itemsAPI.fertileEgg)) {
			e.setHatching(true);
			e.setNumHatches((byte) 1);
			Common.sendABar(e.getPlayer(), "You try hatching the Parrot egg...");
			e.setHatchingType(EntityType.PARROT);
			
		}
		
	}
	
	@EventHandler
	public void onHatch (CreatureSpawnEvent e) {
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.DISPENSE_EGG)) {
			e.setCancelled(true);
		}
		if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.EGG)) {
			if (e.getEntityType().equals(EntityType.PARROT)) {
				Parrot p = (Parrot) e.getEntity();
				p.setTamed(true);
			}
		}
	}
}
