package net.thanhmgz.magicslucchien.commands;

import net.thanhmgz.magicslucchien.MagicsLucChien;
import net.thanhmgz.magicslucchien.System.MagicsPlayer;
import net.thanhmgz.magicslucchien.gui.GUIType;
import net.thanhmgz.magicslucchien.gui.RankingGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        Player player = (Player) sender;
        MagicsPlayer magicsPlayer = MagicsLucChien.getInstance().getMagicsPlayerMap().get(player);
        if (command.getName().equalsIgnoreCase("MagicsLucChien")) {
            if (args.length == 0) {
                player.sendMessage(ChatColor.GREEN + "MagicsLucChien's Commands:");
                player.sendMessage(ChatColor.GRAY + "- " + ChatColor.GREEN + "/mlc ranking " + ChatColor.GRAY + "Mở Bản Xếp Hạng!");
             //   player.sendMessage(ChatColor.GRAY + "- " + ChatColor.GREEN + "/mlc skills " + ChatColor.GRAY + "Mở Menu Quản Lí Các Kĩ năng!");
            } else if (args[0].equalsIgnoreCase("ranking") && args.length == 1) {
                RankingGUI.open(magicsPlayer,true, GUIType.DAILY.getID());
            } else if (args[0].equalsIgnoreCase("skills") && args.length == 1) {
                player.sendMessage("a");
            } else {
                player.sendMessage(ChatColor.RED + "Lệnh Này Không Tồn Tại!");
            }
        }
        return true;
    }
}
