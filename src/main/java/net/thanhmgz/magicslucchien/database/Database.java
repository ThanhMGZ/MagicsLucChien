package net.thanhmgz.magicslucchien.database;


import net.thanhmgz.magicslucchien.MagicsLucChien;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Database {

    private Connection connection;
    private File file;

    public Database() {
        try {
            file = new File(MagicsLucChien.getInstance().getDataFolder(), "database.db");
            if (!file.exists()) {
                file.createNewFile();
            }
            connection = DriverManager.getConnection("jdbc:sqlite:" + file);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void init() {
        try {
            Statement s = connection.createStatement();
            s.executeUpdate("CREATE TABLE IF NOT EXISTS stats (" +
                    "player VARCHAR(64) NOT NULL, " +
                    "points INT NOT NULL, " +
                    "double_kills INT NOT NULL, " +
                    "triple_kills INT NOT NULL, " +
                    "kills INT NOT NULL, " +
                    "PRIMARY KEY (player));");
            s.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getPoint(UUID player) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM stats WHERE player = ?")) {
            ps.setString(1, player.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("player").equals(player.toString())) {
                        return rs.getInt("points");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public List<String> getPlayers() {
        List<String> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM stats");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getString("player"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int getDouble_kills(UUID player) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM stats WHERE player = ?")) {
            ps.setString(1, player.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("player").equals(player.toString())) {
                        return rs.getInt("double_kills");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public int getKills(UUID player) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM stats WHERE player = ?")) {
            ps.setString(1, player.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("player").equals(player.toString())) {
                        return rs.getInt("kills");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public int getTriple_kills(UUID player) {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM stats WHERE player = ?")) {
            ps.setString(1, player.toString());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (rs.getString("player").equals(player.toString())) {
                        return rs.getInt("triple_kills");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }

    public void addPlayer(UUID player) {
        try (PreparedStatement ps = connection.prepareStatement("INSERT OR IGNORE INTO stats (player, points, abilities, double_kills, triple_kills, kills) VALUES (?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, player.toString());
            ps.setInt(2, 0);
            ps.setString(3, "");
            ps.setInt(4, 0);
            ps.setInt(5, 0);
            ps.setInt(6, 0);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePoints(UUID player, int points) {
        // lc đạt mục tiêu sẽ run command
        if (points == 696969696) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"say hello"); //sender là console
        } else if (points == 77*2392929) {
            Bukkit.dispatchCommand(Objects.requireNonNull(Bukkit.getPlayer(player)),""); // sender là player
        }

        try (PreparedStatement ps = connection.prepareStatement("UPDATE stats SET points = ? WHERE player = ?")) {
            ps.setInt(1, points);
            ps.setString(2, player.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDouble_kills(UUID player, int doubleKills) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE stats SET double_kills = ? WHERE player = ?")) {
            ps.setInt(1, doubleKills);
            ps.setString(2, player.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateKills(UUID player, int kills) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE stats SET kills = ? WHERE player = ?")) {
            ps.setInt(1, kills);
            ps.setString(2, player.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTriple_kills(UUID player, int tripleKills) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE stats SET triple_kills = ? WHERE player = ?")) {
            ps.setInt(1, tripleKills);
            ps.setString(2, player.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
