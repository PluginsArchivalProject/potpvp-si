package net.frozenorb.potpvp.kit.menu.kits;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import net.frozenorb.potpvp.PotPvPSI;
import net.frozenorb.potpvp.kit.Kit;
import net.frozenorb.potpvp.kit.menu.editkit.EditKitMenu;
import net.frozenorb.potpvp.kittype.KitType;
import net.frozenorb.qlib.menu.Button;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

final class KitEditButton extends Button {

    private final Kit kit;
    private final KitType kitType;
    private final int slot;

    KitEditButton(Kit kit, KitType kitType, int slot) {
        this.kit = kit;
        this.kitType = Preconditions.checkNotNull(kitType, "kitType");
        this.slot = slot;
    }

    @Override
    public String getName(Player player) {
        return ChatColor.GREEN.toString() + ChatColor.BOLD + "Load/Edit";
    }

    @Override
    public List<String> getDescription(Player player) {
        return ImmutableList.of(
                "",
                ChatColor.YELLOW + "Click to edit this kit."
        );
    }

    @Override
    public Material getMaterial(Player player) {
        return Material.BOOK;
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType) {
        Kit kit = this.kit;

        if (kit == null) {
            kit = PotPvPSI.getInstance().getKitHandler().setDefaultKit(player.getUniqueId(), kitType, this.slot);
        }

        (new EditKitMenu(kit)).openMenu(player);
    }

}