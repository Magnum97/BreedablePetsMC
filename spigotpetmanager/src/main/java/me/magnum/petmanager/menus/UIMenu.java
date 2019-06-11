package me.magnum.petmanager.menus;

import me.kangarko.ui.menu.Menu;
import me.kangarko.ui.menu.MenuButton;
import me.kangarko.ui.menu.menues.MenuStandard;
import me.kangarko.ui.model.ItemCreator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class UIMenu extends MenuStandard {

    private final MenuButton mainButton;

    public UIMenu() {
        super(null);
        this.mainButton = new MenuButton() {
            @Override
            public void onClickedInMenu(Player pl, Menu menu, ClickType click) {
                animateTitle("&ACool title");

            }

            @Override
            public ItemStack getItem() {
                return ItemCreator.of(
                        Material.BONE, "Line 1", "", "Line3").build().make();
            }
        };
    }

    @Override
    protected ItemStack getItemAt(int slot) {
        if (slot == 5) {
            return mainButton.getItem();
        }
        return null;
        }

    @Override
    protected String[] getInfo() {
        return null;
    }

}