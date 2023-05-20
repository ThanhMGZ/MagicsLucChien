package net.thanhmgz.magicslucchien.System.abilities;

import net.minecraft.server.v1_16_R3.ParticleParamRedstone;
import net.minecraft.server.v1_16_R3.ParticleType;
import net.minecraft.server.v1_16_R3.Particles;
import net.thanhmgz.magicslucchien.MagicsLucChien;
import net.thanhmgz.magicslucchien.System.AttactAbility;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_16_R3.CraftParticle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AB1 extends AttactAbility {

    @Override
    public void run(Location location, Player player, int level) {
        Map<Long,Location> map = new HashMap<>();
        int l = getLevel(player);
        int b = l >= 5 && l < 10 ? 5 : l < 15 && l >= 10 ? 8 : l < 20 && l >= 15 ? 12 : l == 20 ? 15 : 3;
        b = 10;
        double playerDamage = 0;
        List<double[]> bp = new ArrayList<>();
        for (int i = 0; i < b; i++) {
            bp.add(getRandomPointInCircle(20));
        }
        int period = l >= 5 && l < 10 ? 12 : l < 15 && l >= 10 ? 10 : l < 20 && l >= 15 ? 7 : l == 20 ? 5 : 15;
        new BukkitRunnable() {
            int i = 0;
            boolean b = false;
            @Override
            public void run() {
                if (!b) {
                    sendParticle(Particles.EXPLOSION,location,0,null);
                    double px = bp.get(i)[0];
                    double pz = bp.get(i)[1];
                    map.put(System.currentTimeMillis() + 500, new Location(location.getWorld(), location.getX() + px, location.getY(), location.getZ() + pz));
                    new BukkitRunnable() {
                        int t = 0;
                        double a = 0;
                        final double p = (double) 180 / 10;

                        @Override
                        public void run() {
                            double py = Math.sin(Math.toRadians(a)) * 4;
                            sendParticle(Particles.FLAME, new Location(player.getWorld(), location.getX() + (px / 10 * (t)), location.getY() + py, location.getZ() +
                                    (pz / 10 * (t))), 0, null);
                            a += p;
                            t++;
                            if (t > 10)
                                cancel();
                        }
                    }.runTaskTimer(MagicsLucChien.getInstance(), 0, 2);
                }
                if ((i + 1) >= bp.size()) {
                    b = true;
                } else {
                    i++;
                }
                if (map.size() == 0)
                    cancel();
                try {
                    for (Long l : map.keySet()) {
                        if (System.currentTimeMillis() >= l) {
                            explosion(map.get(l), playerDamage, player);
                            map.remove(l);
                        }
                    }
                } catch (Exception ignored) {}
            }
        }.runTaskTimer(MagicsLucChien.getInstance(),0,period);
    }

    private void explosion(Location location,double d,Player player) {
      //  sendParticle(Particles.,location,0,null,1);
        Firework fw = location.getWorld().spawn(location,Firework.class);
        FireworkMeta fwm = fw.getFireworkMeta();
        FireworkEffect.Builder ef = FireworkEffect.builder();
        ef.withColor(Color.ORANGE);
        fwm.addEffect(ef.build());
        fw.setFireworkMeta(fwm);
        fw.detonate();
        for (LivingEntity le : location.getNearbyLivingEntities(2)) {
            if (contain(entities(),le.getType())) {
                le.setKiller(player);
                le.damage(d);
            }
        }
    }

    @Override
    public String name() {
        return "Boomer";
    }

    @Override
    public int buyCost() {
        return 0;
    }

    @Override
    public int upgradeCost(int i) {
        return 0;
    }

    @Override
    public int maxLvl() {
        return 0;
    }
}
