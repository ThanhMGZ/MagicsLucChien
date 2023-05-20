package net.thanhmgz.magicslucchien.System.listeners;

import net.thanhmgz.magicslucchien.MagicsLucChien;
import net.thanhmgz.magicslucchien.System.MagicsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InvClose implements Listener {


    @EventHandler
    public void onEvent(InventoryCloseEvent event) {
        MagicsPlayer player = MagicsLucChien.getInstance().getMagicsPlayerMap().get((Player) event.getPlayer());
        if (player.getInventory() != null)
            player.setInventory(null);
    }
}
