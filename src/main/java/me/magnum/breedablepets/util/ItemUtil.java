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
					.addLoreLine("Did it just move?")
					.addLoreLine("I think it is ready to hatch!")
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
