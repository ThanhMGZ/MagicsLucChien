package net.thanhmgz.magicslucchien.System.listeners;

import net.thanhmgz.magicslucchien.MagicsLucChien;
import net.thanhmgz.magicslucchien.System.MagicsPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class JoinEvent implements Listener {

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (MagicsLucChien.getInstance().getDatabase().getPoint(player.getUniqueId()) == -1) {
            MagicsLucChien.getInstance().getDatabase().addPlayer(player.getUniqueId());
        }
        if (!MagicsLucChien.getInstance().getDailyKills().containsKey(player.getUniqueId().toString())) {
            MagicsLucChien.getInstance().getDailyKills().put(player.getUniqueId().toString(),new ArrayList<>());
        }
        if (!MagicsLucChien.getInstance().getMagicsPlayerMap().containsKey(player)) {
            MagicsPlayer mp = new MagicsPlayer(player);
            MagicsLucChien.getInstance().getMagicsPlayerMap().put(player,mp);
        }
    }
}
