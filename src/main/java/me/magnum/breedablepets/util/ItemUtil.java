package me.magnum.breedablepets.util;

import me.magnum.lib.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemUtil {
	
	public ItemStack regEgg = makeEgg();
	public ItemStack fertileEgg = makeEgg(true);
	
	public ItemUtil () {
	}
	
	private ItemStack makeEgg () {
		return makeEgg(false);
	}
	
	private ItemStack makeEgg (boolean fertile) {
		ItemStack egg;
		if (fertile) {
			egg = new ItemBuilder(Material.EGG)
					.setName("§eFertile Parrot Egg")
					.addLoreLine("I think it moved!")
					.addLoreLine("I bet it is ready to hatch!")
					.toItemStack();
		}
		else {
			egg = new ItemBuilder(Material.EGG)
					.setName("§aParrot Egg")
					.addLoreLine("We don't know how it got here,")
					.addLoreLine("but it might hatch a parrot!")
					.toItemStack();
		}
		return egg;
	}
	
	public ItemStack birdFood () {
		ItemStack bf = new ItemBuilder(Material.PUMPKIN_SEEDS).toItemStack();
		return bf;
	}
}
