package net.thanhmgz.magicslucchien.System;

import net.thanhmgz.magicslucchien.MagicsLucChien;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class Cooldown {

    private Map<Player,Long> map = new HashMap<>();

    public void add(Player player, long cd) {
        if (contain(player)) {
            map.replace(player,System.currentTimeMillis() + cd);
            return;
        }
        map.put(player,System.currentTimeMillis() + cd);
    }

    public void remove(Player player) {
        if (!contain(player))
            return;
        map.remove(player);
    }

    public double timeLeft(Player player) {
        if (!contain(player))
            return -1;
        long t = map.get(player) - System.currentTimeMillis();
        return Double.parseDouble(String.format("%.1f",(double) t / 1000));
    }
    public boolean contain(Player player) {
        if (!map.containsKey(player))
            return false;
        if (map.get(player) > System.currentTimeMillis()) {
            return true;
        } else {
            map.remove(player);
            MagicsLucChien.getInstance().getMagicsPlayerMap().get(player).setComboKillsStreak(1);
            return false;
        }
    }
}
