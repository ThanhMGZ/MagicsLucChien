package net.thanhmgz.magicslucchien;

import net.thanhmgz.magicslucchien.System.*;
import net.thanhmgz.magicslucchien.System.abilities.AB1;
import net.thanhmgz.magicslucchien.System.listeners.InvClick;
import net.thanhmgz.magicslucchien.System.listeners.InvClose;
import net.thanhmgz.magicslucchien.System.listeners.JoinEvent;
import net.thanhmgz.magicslucchien.System.listeners.PlayerDeath;
import net.thanhmgz.magicslucchien.commands.AdminCommand;
import net.thanhmgz.magicslucchien.commands.MainCommand;
import net.thanhmgz.magicslucchien.database.Database;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public final class  MagicsLucChien extends JavaPlugin {

    private static MagicsLucChien instance;

    private Database database;

    private LocalDateTime ldt = LocalDateTime.now(ZoneId.of("UTC+7"));

    private Map<String, List<String>> dailyKills = new HashMap<>();

    private Map<Player, MagicsPlayer> magicsPlayerMap = new HashMap<>();

    private Map<String, Ability> abilityMap = new HashMap<>();

    private Listener[] listeners = {new PlayerDeath(),new JoinEvent(), new InvClick(), new InvClose()};

    private Cooldown cooldown;

    private Map<String, DefendAbility> defendAbilityMap = new HashMap<>();

    private Map<String, AttactAbility> attactAbilityMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        for (Listener ls : listeners) {
            getServer().getPluginManager().registerEvents(ls,this);
        }
        getCommand("MagicsLucChien").setExecutor(new MainCommand());
        getCommand("MagicsLucChienAdmin").setExecutor(new AdminCommand());
        this.cooldown = new Cooldown();
        if (!new File(getDataFolder(),"config.yml").exists())
            saveResource("config.yml",true);
        instance = this;
        this.database = new Database();
        this.database.init();
        for (Player p : Bukkit.getOnlinePlayers()) {
            MagicsLucChien.getInstance().getDatabase().addPlayer(p.getUniqueId());
            dailyKills.put(String.valueOf(p.getUniqueId()),new ArrayList<>());
            MagicsPlayer mp = new MagicsPlayer(p);
            getMagicsPlayerMap().put(p,mp);
        }
        updateDailyKills();
        getConfig().set("DailyKills","");
        saveConfig();
        initAbility();
    }

    public void updateDailyKills() {
        if (getConfig().contains("time")) {
            if (Objects.equals(getConfig().getString("time"), "-")) {
                setTimeInCF();
            }
            if (ldt.getDayOfMonth() != Integer.parseInt(getConfig().getString("time").split(";")[0])) {
                setTimeInCF();
                getConfig().set("DailyKills","");
                saveConfig();
                dailyKills.clear();
                return;
            }
        }
        if (getConfig().contains("DailyKills")) {
            try {
                for (String uid : getConfig().getConfigurationSection("DailyKills").getKeys(false)) {
                    String[] k = Objects.requireNonNull(getConfig().getString("DailyKills." + uid)).split(";");
                    List<String> s = Arrays.asList(k);
                    dailyKills.put(uid,s);
                }
            }catch (Exception ignored) {}
        }
    }

    private void initAbility() {
        Ability ab1 = new AB1();
        getAbilityMap().put(ab1.name(),ab1);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        database.close();
        if (dailyKills.size() == 0)
            return;
        for (String s : dailyKills.keySet()) {
            StringBuilder v = new StringBuilder();
            for (String s1 : dailyKills.get(s)) {
                v.append(s1).append(";");
            }
            if (v.length() > 0)
                v.deleteCharAt(v.length() - 1);
            getConfig().set("DailyKills." + s, v.toString());
        }
    }

    public void setTimeInCF() {
        getConfig().set("time", ldt.getDayOfMonth() + ";" + ldt.getMonthValue() + ";" + ldt.getYear());
    }

    public Map<String, List<String>> getDailyKills() {
        return dailyKills;
    }

    public Cooldown getCooldown() {
        return cooldown;
    }

    public Database getDatabase() {
        return database;
    }

    public LocalDateTime getLdt() {
        return ldt;
    }

    public Map<Player, MagicsPlayer> getMagicsPlayerMap() {
        return magicsPlayerMap;
    }

    public static MagicsLucChien getInstance() {
        return instance;
    }

    public Map<String, Ability> getAbilityMap() {
        return abilityMap;
    }

    public Map<String, AttactAbility> getAttactAbilityMap() {
        return attactAbilityMap;
    }

    public Map<String, DefendAbility> getDefendAbilityMap() {
        return defendAbilityMap;
    }
}
