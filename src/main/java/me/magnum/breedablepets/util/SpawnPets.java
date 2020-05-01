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

import me.magnum.breedablepets.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;

import java.util.Objects;


public class SpawnPets {
	

	public SpawnPets () {
	}
	
	public static void newParrot (Player player, Location location) {
		World world = player.getWorld();
		Parrot parrot = (Parrot) world.spawnEntity(location, EntityType.PARROT);
		parrot.setTamed(Main.getCfg().getBoolean("hatches.tamed"));
		parrot.setHealth(1); //todo add to config
		parrot.setVariant(Parrot.Variant.RED);
		if (parrot.isTamed()) {
			parrot.setOwner(player);
			parrot.setSitting(true);
		}
		if (Main.getCfg().getBoolean("hatches.named")) {
			parrot.setCustomNameVisible(true);
			parrot.setCustomName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(Main.getCfg().getString("hatches.name"))));
		}
	}
}
