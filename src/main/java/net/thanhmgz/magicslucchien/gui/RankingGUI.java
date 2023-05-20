package net.thanhmgz.magicslucchien.gui;

import net.thanhmgz.magicslucchien.MagicsLucChien;
import net.thanhmgz.magicslucchien.System.ItemBuilder;
import net.thanhmgz.magicslucchien.System.MagicsPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class RankingGUI {

    private static int[] slots = new int[0];

    public static Inventory open(MagicsPlayer player,boolean open,int guiType) {
        Inventory inventory = Bukkit.createInventory(null,54,"Bản Xếp Hạng");
        Inventory inventory2 = Bukkit.createInventory(null,54,"Bản Xếp Hạng");
        if (slots.length == 0) {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (i >= 12 && i <= 16
                    ||
                 (i >= 21 && i <= 25)
                    ||
                 (i >= 30 && i <= 34)
                    ||
                 (i >= 39 && i <= 43)) {
                    slots = Arrays.copyOf(slots, slots.length + 1);
                    slots[slots.length - 1] = i;
                }
            }
        }
        for (int i = 0; i < inventory.getSize(); i++) {
            if (!contain(i)) {
                inventory.setItem(i, new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE)
                        .setDisplayName("&0")
                        .build());
            }
        }
        inventory.setItem(0,new ItemBuilder(Material.PLAYER_HEAD).setHead(player.getPlayer().getName())
                .setDisplayName(player.getPlayer().getDisplayName())
                .addLore("&7- Hôm Nay: " + MagicsLucChien.getInstance().getDailyKills().get(player.getPlayer().getUniqueId().toString()).size())
                .addLore("&7- Kills: " + MagicsLucChien.getInstance().getDatabase().getKills(player.getPlayer().getUniqueId()))
                .addLore("&7- Double Kills: " + MagicsLucChien.getInstance().getDatabase().getDouble_kills(player.getPlayer().getUniqueId()))
                .addLore("&7- Triple Kills: " + MagicsLucChien.getInstance().getDatabase().getTriple_kills(player.getPlayer().getUniqueId()))
                .addLore("&7- Points : " + MagicsLucChien.getInstance().getDatabase().getPoint(player.getPlayer().getUniqueId())).build());

        inventory.setItem(1,new ItemBuilder("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDk3NTNhOTU2MjIxOGI1ZTAzZTdkNGY5M2QyMDcxZmI3ZmY0ZDVkYzk4Yjk4YjAwMWY1ZGQwODQyMTUzNTg2MyJ9fX0=")
                .setDisplayName("&aTop Daily Kills")
                .build());
        inventory.setItem(10,new ItemBuilder(Material.IRON_SWORD)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addEnchant(Enchantment.KNOCKBACK,1)
                .setDisplayName("&aTop Kills")
                .build());
        inventory.setItem(19,new ItemBuilder(Material.GOLDEN_SWORD)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addEnchant(Enchantment.KNOCKBACK,1)
                .setDisplayName("&aTop Double Kills")
                .build());
        inventory.setItem(28,new ItemBuilder(Material.DIAMOND_SWORD)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addEnchant(Enchantment.KNOCKBACK,1)
                .setDisplayName("&aTop Triple Kills")
                .build());
        inventory.setItem(37,new ItemBuilder(Material.GOLD_INGOT)
                .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                .addEnchant(Enchantment.KNOCKBACK,1)
                .setDisplayName("&aTop Points")
                .build());
        inventory2.setStorageContents(inventory.getStorageContents());
        int i = 0;
        if (guiType == GUIType.DAILY.getID()) {
            Map<String,Integer> top = MagicsPlayer.getTopDailyKills(slots.length);
            for (String k : top.keySet()) {
                inventory.setItem(slots[i], new ItemBuilder(Material.PLAYER_HEAD).setHead(k)
                        .setDisplayName(ChatColor.GREEN + Bukkit.getOfflinePlayer(UUID.fromString(k)).getName())
                        .addLore("§7 Số Lượng: " + top.get(k)).build());
                i++;
            }
        } else if (guiType == GUIType.TOP_KILLS.getID()) {
            Map<String,Integer> top = MagicsPlayer.getTopKills(slots.length);
            for (String k : top.keySet()) {
                inventory.setItem(slots[i], new ItemBuilder(Material.PLAYER_HEAD).setHead(k)
                        .setDisplayName(ChatColor.GREEN + Bukkit.getOfflinePlayer(UUID.fromString(k)).getName())
                        .addLore("§7 Số Lượng: " + top.get(k)).build());
                i++;
            }
        }else if (guiType == GUIType.TOP_DOUBLE_KILLS.getID()) {
            Map<String,Integer> top = MagicsPlayer.getTopDoubleKills(slots.length);
            for (String k : top.keySet()) {
                inventory.setItem(slots[i], new ItemBuilder(Material.PLAYER_HEAD).setHead(k)
                        .setDisplayName(ChatColor.GREEN + Bukkit.getOfflinePlayer(UUID.fromString(k)).getName())
                        .addLore("§7 Số Lượng: " + top.get(k)).build());
                i++;
            }
        }else if (guiType == GUIType.TOP_TRIPLE_KILLS.getID()) {
            Map<String,Integer> top = MagicsPlayer.getTopTripleKills(slots.length);
            for (String k : top.keySet()) {
                inventory.setItem(slots[i], new ItemBuilder(Material.PLAYER_HEAD).setHead(k)
                        .setDisplayName(ChatColor.GREEN + Bukkit.getOfflinePlayer(UUID.fromString(k)).getName())
                        .addLore("§7 Số Lượng: " + top.get(k)).build());
                i++;
            }
        }else if (guiType == GUIType.TOP_POINTS.getID()) {
            Map<String,Integer> top = MagicsPlayer.getTopPoints(slots.length);
            for (String k : top.keySet()) {
                inventory.setItem(slots[i], new ItemBuilder(Material.PLAYER_HEAD).setHead(k)
                        .setDisplayName(ChatColor.GREEN + Bukkit.getOfflinePlayer(UUID.fromString(k)).getName())
                        .addLore("§7 Số Lượng: " + top.get(k)).build());
                i++;
            }
        }
        if (open) {
            player.getPlayer().openInventory(inventory);
            player.setInventory(inventory2);
            player.setInvId(guiType);
        }
        return inventory2;
    }

    private static boolean contain(int i) {
        for (int ii : slots)
            if (ii == i)
                return true;
        return false;
    }
}
