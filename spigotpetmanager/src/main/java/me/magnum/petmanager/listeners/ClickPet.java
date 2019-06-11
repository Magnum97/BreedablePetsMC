package me.magnum.petmanager.listeners;


import me.magnum.lib.Common;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;

public class ClickPet implements Listener {

//    public final String pre = "&9[&6PetManager&9] ";


    @EventHandler
    private void onClick(PlayerInteractEntityEvent pie) {
        String pre = "&9[&6PetManager&9] ";
        Player player = pie.getPlayer();
        Entity target = pie.getRightClicked();
        Material hand = player.getInventory().getItemInMainHand().getType();
        Material tool = Material.BONE;
        Material stick = Material.STICK;
        String pet;
        String owner;
        String name = player.getPlayer().getName();

        UUID oid = player.getUniqueId();
        UUID eid = target.getUniqueId();
        String isAdmin = "petmanager.admin";

        if (pie.getHand() != EquipmentSlot.HAND) {
            return;
        }
        if (!name.equals("Magnum1997")) {
            if (!player.hasPermission(isAdmin)) {
//                Common.tell(pl, pre + "&eYou need to be Magnum or have " + isadmin + " permission to so that.");
                return;
            }
            Common.tell(player, pre + "&eBypass permission detected.");
        }
        if (hand == tool) {
//            Common.tell(pl,ChatColor.AQUA + "You can check pets with this " + tool.toString().toLowerCase());

            if (target.getType() == EntityType.MUSHROOM_COW) {
                pet = "Moo-shroom";
            } else {
                pet = target.getType().toString().toLowerCase();
            }
            // If target is tameable :
            if (target instanceof Tameable) {
                if (((Tameable) target).isTamed()) {
                    if (target.getType() == EntityType.OCELOT) {
                        pet = "cat";
                    } else if (target.getType() == EntityType.WOLF) {
                        pet = "dog";
                    }
                    owner = (((Tameable) target).getOwner().getName());
                    if (owner.equals(player.toString())) {
                        Common.tell(player, pre + "This is your " + pet);
                    } else if (!owner.equals(player.toString())) {
                        Common.tell(player, pre + ChatColor.YELLOW + "This " + pet + " is owned by " + ChatColor.WHITE + owner);
                    } else {
                        Common.tell(player, pre + ChatColor.WHITE + "This " + pet + ChatColor.GREEN + " is tamed but " + ChatColor.RED + " not claimed.");
                    }
                } else {
                    // If tameable not not tamed
                    Common.tell(player, pre + ChatColor.WHITE + "This " + pet + " is wild and can be tamed with " + pet + ChatColor.GREEN + " is tameable.");
                }
            }
            // not tameable message:
            if (!(target instanceof Tameable)) {
                Common.tell(player, pre + ChatColor.YELLOW + "You can not tame a " + pet);
            }
        }
        if (hand == stick) {
            Common.tell(player, pre + "&fPlayer UUID:&e " + oid, pre + "&fEntity UUID:&e " + eid);
        }
        pie.setCancelled(true);
    }
}

