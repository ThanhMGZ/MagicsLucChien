package net.thanhmgz.magicslucchien.System.listeners;

import net.thanhmgz.magicslucchien.MagicsLucChien;
import net.thanhmgz.magicslucchien.System.MagicsPlayer;
import net.thanhmgz.magicslucchien.gui.GUIType;
import net.thanhmgz.magicslucchien.gui.RankingGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvClick implements Listener {

    @EventHandler
    public void onEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        MagicsPlayer magicsPlayer = MagicsLucChien.getInstance().getMagicsPlayerMap().get(player);
        if (magicsPlayer.getInventory() != null) {
         //   if (magicsPlayer.getInventory().equals(RankingGUI.open(magicsPlayer,false,magicsPlayer.getInvId()))) {
                event.setCancelled(true);
                if (event.getSlot() == 1) {
                    RankingGUI.open(magicsPlayer,true,GUIType.DAILY.getID());
                }else if (event.getSlot() == 10) {
                    RankingGUI.open(magicsPlayer,true,GUIType.TOP_KILLS.getID());
                }else if (event.getSlot() == 19) {
                    RankingGUI.open(magicsPlayer,true,GUIType.TOP_DOUBLE_KILLS.getID());
                }else if (event.getSlot() == 28) {
                    RankingGUI.open(magicsPlayer,true,GUIType.TOP_TRIPLE_KILLS.getID());
                }else if (event.getSlot() == 37) {
                    RankingGUI.open(magicsPlayer,true,GUIType.TOP_POINTS.getID());
                }
            }
       // }
    }
}
