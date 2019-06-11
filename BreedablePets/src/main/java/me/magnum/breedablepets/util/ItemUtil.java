package me.magnum.breedablepets.util;

import me.magnum.lib.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {
	
	public ItemStack egg = makeEgg();
	
	public ItemUtil () {
	}
	
	public ItemStack makeEgg () {
		ItemStack newegg = new ItemBuilder(Material.EGG)
				.setName("§aParrot egg").toItemStack();
		return newegg;
	}
	public ItemStack birdFood(){
		ItemStack bf = new ItemBuilder(Material.PUMPKIN_SEEDS).toItemStack();
		return bf;
	}
}
