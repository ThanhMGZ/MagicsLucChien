package net.thanhmgz.magicslucchien.System.listeners;

import net.thanhmgz.magicslucchien.MagicsLucChien;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onEvent(PlayerDeathEvent event) {
        if (event.getEntity().getKiller() == null)
            return;
        Player killer = event.getEntity().getKiller();
        Player chicken = event.getEntity();
        if (MagicsLucChien.getInstance().getDailyKills().get(killer.getUniqueId().toString()).contains(chicken.getUniqueId().toString())) {
            if (MagicsLucChien.getInstance().getDailyKills().get(killer.getUniqueId().toString()).size() >= 10) {
                return;
            }
        }
        MagicsLucChien.getInstance().updateDailyKills();
        MagicsLucChien.getInstance().getDatabase().updatePoints(killer.getUniqueId(),
                MagicsLucChien.getInstance().getDatabase().getPoint(killer.getUniqueId()) + new Random().nextInt(60 - 40 + 1) + 40);
        int ks = MagicsLucChien.getInstance().getMagicsPlayerMap().get(killer).getComboKillsStreak() + 1;
        killer.sendMessage(String.valueOf(MagicsLucChien.getInstance().getDatabase().getPoint(killer.getUniqueId())));
        if (!MagicsLucChien.getInstance().getCooldown().contain(killer)) {
            MagicsLucChien.getInstance().getMagicsPlayerMap().get(killer).setComboKillsStreak(1);
            ks = 1;
        } else {
            MagicsLucChien.getInstance().getMagicsPlayerMap().get(killer).setComboKillsStreak(ks);
        }
        if (ks == 2) {
            MagicsLucChien.getInstance().getDatabase().updateDouble_kills(killer.getUniqueId(),
                    MagicsLucChien.getInstance().getDatabase().getDouble_kills(killer.getUniqueId()) + 1);
            sendMes(ChatColor.AQUA + "DOUBLE KILL! " + killer.getDisplayName() + ChatColor.WHITE + " Đã Giết Chết " + chicken.getDisplayName() + ChatColor.WHITE + "!");
        } else if (ks == 3) {
            MagicsLucChien.getInstance().getDatabase().updateTriple_kills(killer.getUniqueId(),
                    MagicsLucChien.getInstance().getDatabase().getTriple_kills(killer.getUniqueId()) + 1);
            sendMes(ChatColor.RED + "TRIPLE KILL! " + killer.getDisplayName() + ChatColor.WHITE + " Đã Giết Chết " + chicken.getDisplayName() + ChatColor.WHITE + "!");
            MagicsLucChien.getInstance().getCooldown().remove(killer);
        } else if (ks == 1) {
            sendMes(ChatColor.YELLOW + "KILL! " + killer.getDisplayName() + ChatColor.WHITE + " Đã Giết Chết " + chicken.getDisplayName() + ChatColor.WHITE + "!");
        } else if (ks >= 4) {
            sendMes(ChatColor.DARK_RED + "KILL! " + killer.getDisplayName() + ChatColor.WHITE + " Đã Giết Chết " + chicken.getDisplayName() + ChatColor.DARK_RED + "(" + ks + " Kill-streak)" );
        }
        
        MagicsLucChien.getInstance().getDatabase().updateKills(killer.getUniqueId(),MagicsLucChien.getInstance().getDatabase().getKills(killer.getUniqueId()) + 1);
        List<String> strs = (MagicsLucChien.getInstance().getDailyKills().get(killer.getUniqueId().toString()));
        strs.add(chicken.getUniqueId().toString());
        MagicsLucChien.getInstance().getDailyKills().replace(killer.getUniqueId().toString(),strs);
        MagicsLucChien.getInstance().getCooldown().add(killer,System.currentTimeMillis() + 5000);
    }


    private void sendMes(String mes) {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(mes);
    }
}
