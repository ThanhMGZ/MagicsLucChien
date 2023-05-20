package net.thanhmgz.magicslucchien.System;

import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.ParticleParamRedstone;
import net.minecraft.server.v1_16_R3.ParticleType;
import net.minecraft.server.v1_16_R3.Particles;
import net.thanhmgz.magicslucchien.MagicsLucChien;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;

public abstract class Ability {

    public abstract void run(Location location, Player player,int level);

    public abstract String name();

    public abstract int buyCost();

    public abstract int upgradeCost(int i);

    public abstract int maxLvl();

    public int getLevel(Player player) {
        try {
            return MagicsLucChien.getInstance().getDatabase().getAbilities(player.getUniqueId()).get(name());
        } catch (Exception e) {
            return 0;
        }
    }

    private static Map<Integer,double[][]> circle = new HashMap<>();


    public double[] getRandomPointInCircle(double z) {
        double z1 = z / 100;
        double[] v = getRandomPointInCircle();
        while ((v[0] > 0 && v[0] < z1) || (v[0] < 0 && v[0] > -z1) ||
               (v[1] > 0 && v[1] < z1) || (v[1] < 0 && v[1] > -z1)) {
            v = getRandomPointInCircle();
        }
        v[0] = v[0] * z;
        v[1] = v[1] * z;
        return v;
    }

    private double[] getRandomPointInCircle() {
        double t = 2 * Math.PI * Math.random();
        double r = Math.sqrt(Math.random());
        double[] d = new double[2];
        d[0] = r * Math.cos(t);
        d[1] = r * Math.sin(t);
        return d;
    }

    public double[][] getCircle(int i) {
        if (circle.containsKey(i))
            return circle.get(i);
        double[][] d = new double[2][i];
        double a = Math.PI * 2 / i;
        for (int j = 0; j < i; j++) {
            d[0][j] = Math.cos(a * (j + 1));
            d[1][j] = Math.sin(a * (j + 1));
        }
        circle.put(i,d);
        return d;
    }

    public void drawLine(Location origin, Location entity, ParticleType particle) {
        Location v = entity.clone().subtract(origin.clone());
        double px = v.getX() / 50;
        double py = v.getY() / 50;
        double pz = v.getZ() / 50;
        for (int i = 0; i < 50; i++) {
            sendParticle(particle,new Location(origin.getWorld(),origin.getX() + (px * (i + 1)),
                    origin.getY() + (py * (i + 1)),origin.getZ() + (pz * (i + 1))),0,null);
        }
    }

    public static void drawLine(Location origin, Location entity, Color color, float size) {
        Location v = entity.clone().subtract(origin.clone());
        double px = v.getX() / 50;
        double py = v.getY() / 50;
        double pz = v.getZ() / 50;
        for (int i = 0; i < 50; i++) {
            sendParticle(color,new Location(origin.getWorld(),origin.getX() + (px * (i + 1)),
                    origin.getY() + (py * (i + 1)),origin.getZ() + (pz * (i + 1))),0,null,size);
        }
    }


    public double tickToSecond(int t) {
        return 0.05 * t;
    }

    public EntityType[] entities() {
        return new EntityType[] {
                EntityType.BAT,EntityType.BEE,EntityType.CAT,EntityType.COD,EntityType.BLAZE,EntityType.CAVE_SPIDER,
                EntityType.CHICKEN,EntityType.COW,EntityType.CREEPER,EntityType.DOLPHIN,EntityType.ENDER_DRAGON,EntityType.ENDERMAN,
                EntityType.ELDER_GUARDIAN,EntityType.ENDERMITE,EntityType.FOX,EntityType.GHAST,EntityType.GIANT,EntityType.GUARDIAN,
                EntityType.HORSE,EntityType.SKELETON_HORSE,EntityType.SKELETON,EntityType.WITCH,EntityType.WITHER_SKELETON,EntityType.HUSK,
                EntityType.IRON_GOLEM,EntityType.PANDA,EntityType.PIG,EntityType.PIGLIN,EntityType.MAGMA_CUBE,EntityType.SLIME,EntityType.MUSHROOM_COW,
                EntityType.OCELOT,EntityType.PARROT,EntityType.PHANTOM,EntityType.PIGLIN_BRUTE,EntityType.PILLAGER,EntityType.PLAYER,EntityType.POLAR_BEAR,
                EntityType.SNOWMAN,EntityType.RABBIT,EntityType.SHEEP,EntityType.SPIDER,EntityType.SQUID,EntityType.WOLF,EntityType.ZOGLIN,EntityType.ZOMBIE,
                EntityType.ZOMBIE_HORSE,EntityType.ZOMBIE_VILLAGER,EntityType.ZOMBIFIED_PIGLIN,EntityType.HOGLIN,EntityType.RAVAGER,EntityType.SALMON,
                EntityType.SILVERFISH,EntityType.VILLAGER,EntityType.TURTLE
        };
    }

    public boolean getNearsByCAEntities(double r,Location loc) {
        for (LivingEntity e : loc.getNearbyLivingEntities(r)) {
            if (contain(entities(),e)) {
                return true;
            }
        }
        return false;
    }

    public static void sendParticle(ParticleType particle, Location loc, float speed, Vector v) {
        PacketPlayOutWorldParticles packet;
        if (v != null) {
            packet = new PacketPlayOutWorldParticles(particle,true,loc.getX(),loc.getY(),loc.getZ(),
                    (float) v.getX(),(float) v.getY(),(float) v.getZ(),speed,0);
        } else {
            packet = new PacketPlayOutWorldParticles(particle,true,loc.getX(),loc.getY(),loc.getZ(),0,0,0,speed,0);
        }
        for (Player player : loc.getNearbyPlayers(64)) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public void sendParticle(ParticleType particle, Location loc, float speed, Vector v,int c) {
        PacketPlayOutWorldParticles packet;
        if (v != null) {
            packet = new PacketPlayOutWorldParticles(particle,true,loc.getX(),loc.getY(),loc.getZ(),
                    (float) v.getX(),(float) v.getY(),(float) v.getZ(),speed,c);
        } else {
            packet = new PacketPlayOutWorldParticles(particle,true,loc.getX(),loc.getY(),loc.getZ(),0,0,0,speed,c);
        }
        for (Player player : loc.getNearbyPlayers(64)) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }
    }


    public static void sendParticle(Color particle, Location loc, float speed, Vector v, float size) {
        PacketPlayOutWorldParticles packet;
        if (v != null) {
            packet = new PacketPlayOutWorldParticles(new ParticleParamRedstone((float) particle.getRed() / 255 , (float) particle.getGreen() / 255, (float) particle.getBlue() / 255,size), false, loc.getX(), loc.getY(), loc.getZ(),
                    (float) v.getX(), (float) v.getY(), (float) v.getZ(), speed, 0);
        } else {
            packet = new PacketPlayOutWorldParticles(new ParticleParamRedstone((float) particle.getRed() / 255 , (float) particle.getGreen() / 255, (float) particle.getBlue() / 255,size), false, loc.getX(), loc.getY(), loc.getZ(), 0, 0, 0, speed, 0);
        }
        for (Player player : loc.getNearbyPlayers(64)) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public boolean contain(Object[] list, Object o) {
        for (Object ob : list) {
            if (ob.equals(o))
                return true;
        }
        return false;
    }

}
