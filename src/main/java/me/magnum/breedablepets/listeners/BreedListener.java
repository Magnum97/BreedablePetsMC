package me.magnum.breedablepets.listeners;

import me.magnum.breedablepets.util.ItemUtil;
import me.magnum.lib.Common;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class BreedListener implements Listener {
	
	private ItemUtil items = new ItemUtil();
	
	public BreedListener () {
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onFeed (PlayerInteractEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}
		// if (!e.getRightClicked().getType().equals(EntityType.PARROT)) {
		// 	return;
		// }
		// if (!e.getPlayer().getInventory().getItemInMainHand().equals(items.birdFood())){
		// 	return;
		// }
		Player player = e.getPlayer();
		Entity target = e.getRightClicked();
		String type = target.getType().toString().toLowerCase();
		Common.tell(player, "That is a " + type,
		            "There are " + player.getNearbyEntities(5, 5, 5) + " entities near.");
		
		// List<Entity> nearby = one.getNearbyEntities(2, 2, 2);
		// nearby.forEach(entity -> {
		// 	if (entity.getType().equals(one.getType())){
		// 		one.getWorld().dropItemNaturally(one.getLocation(), items.regEgg.clone());
		// 	}
		// });
	}
}
