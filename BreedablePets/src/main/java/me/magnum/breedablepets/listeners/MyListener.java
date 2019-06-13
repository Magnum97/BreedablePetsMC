package me.magnum.breedablepets.listeners;

import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.breedablepets.util.SpawnPets;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class MyListener implements Listener {
	
	private ItemUtil itemsAPI = new ItemUtil();
	private SpawnPets spawner = new SpawnPets();
	
	@EventHandler
	public void onEggThrow (PlayerEggThrowEvent e) {
		if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(itemsAPI.regEgg)) {
			if (e.isHatching()) {
				e.setHatchingType(EntityType.PARROT);
			}
		}
		if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(itemsAPI.fertileEgg)) {
			e.setHatching(true);
			e.setHatchingType(EntityType.PARROT);
		}
		
	}
}
