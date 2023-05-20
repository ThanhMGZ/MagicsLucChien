package net.thanhmgz.magicslucchien.System;

import net.thanhmgz.magicslucchien.MagicsLucChien;
import net.thanhmgz.magicslucchien.database.Database;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class MagicsPlayer {

    private Player player;

    private long cd;

    private Inventory inventory = null;

    private int comboKillsStreak = 1, invId = 0;

    public MagicsPlayer(Player player) {
        this.player = player;
    }

    public int getComboKillsStreak() {
        return comboKillsStreak;
    }

    public void setComboKillsStreak(int comboKillsStreak) {
        this.comboKillsStreak = comboKillsStreak;
    }

    public void setCd(long cd) {
        this.cd = cd;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public long getCd() {
        return cd;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getInvId() {
        return invId;
    }


    public void setInvId(int invId) {
        this.invId = invId;
    }

    public static Map<String,Integer> getTopPoints(int limit) {
        Database database = MagicsLucChien.getInstance().getDatabase();
        Map<String,Integer> map = new HashMap<>();
        for (String uid : database.getPlayers()) {
            map.put(uid,database.getPoint(UUID.fromString(uid)));
        }
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(limit).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2) -> e1, LinkedHashMap::new));
    }

    public static Map<String,Integer> getTopPointsLF(int limit) {
        Database database = MagicsLucChien.getInstance().getDatabase();
        Map<String,Integer> map = new HashMap<>();
        for (String uid : database.getPlayers()) {
            int abilitiesPoints = 0;
            Map<String,Integer> abilities = database.getAbilities(UUID.fromString(uid));
            for (String str : abilities.keySet()) {
                Ability ab = MagicsLucChien.getInstance().getAbilityMap().get(str);
                abilitiesPoints += ab.buyCost();
                for (int i = 0; i < abilities.get(str); i++) {
                    abilitiesPoints += ab.upgradeCost(i + 1);
                }
            }
            map.put(uid,database.getPoint(UUID.fromString(uid)) + abilitiesPoints);
        }
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(limit).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2) -> e1, LinkedHashMap::new));
    }

    public static Map<String,Integer> getTopKills(int limit) {
        Database database = MagicsLucChien.getInstance().getDatabase();
        Map<String,Integer> map = new HashMap<>();
        for (String uid : database.getPlayers()) {
            map.put(uid,database.getKills(UUID.fromString(uid)));
        }
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(limit).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2) -> e1, LinkedHashMap::new));
    }
    public static Map<String,Integer> getTopDoubleKills(int limit) {
        Database database = MagicsLucChien.getInstance().getDatabase();
        Map<String,Integer> map = new HashMap<>();
        for (String uid : database.getPlayers()) {
            map.put(uid,database.getDouble_kills(UUID.fromString(uid)));
        }
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(limit).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2) -> e1, LinkedHashMap::new));
    }
    public static Map<String,Integer> getTopTripleKills(int limit) {
        Database database = MagicsLucChien.getInstance().getDatabase();
        Map<String,Integer> map = new HashMap<>();
        for (String uid : database.getPlayers()) {
            map.put(uid,database.getTriple_kills(UUID.fromString(uid)));
        }
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(limit).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2) -> e1, LinkedHashMap::new));
    }

    public static Map<String,Integer> getTopDailyKills(int limit) {
        Map<String,Integer> map = new HashMap<>();
        for (String player : MagicsLucChien.getInstance().getDailyKills().keySet()) {
            map.put(player,MagicsLucChien.getInstance().getDailyKills().get(player).size() );
        }
        return map.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(limit).collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(e1,e2)-> e1, LinkedHashMap::new));
    }

}
