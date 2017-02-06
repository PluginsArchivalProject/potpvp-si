package net.frozenorb.potpvp.kit.listener;

import net.frozenorb.potpvp.kit.menu.editkit.EditKitMenu;
import net.frozenorb.qlib.menu.Menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class KitEditorListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCursor() == null || event.getCursor().getType() == Material.AIR) {
            return;
        }

        if (event.getClickedInventory() != event.getView().getTopInventory()) {
            return;
        }

        if (Menu.currentlyOpenedMenus.get(player.getName()) instanceof EditKitMenu) {
            event.setCancelled(true);
        }
    }

}