package me.magnum.breedablepets.listeners;

import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.breedablepets.util.SpawnPets;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;

public class MyListener implements Listener {
	
	private ItemUtil itemsAPI = new ItemUtil();
	private SpawnPets spawner = new SpawnPets();
	
	@EventHandler
	public void onInventoryClick (PlayerEggThrowEvent e) {
		if (e.getPlayer().getInventory().getItemInMainHand().isSimilar(itemsAPI.egg)) {
			if (e.isHatching()) {
				e.setHatching(false);
				Player p = e.getPlayer();
				Egg egg = e.getEgg();
				egg.getLocation();
				spawner.newParrot(p, egg.getLocation());
			}
		}
	}
}
