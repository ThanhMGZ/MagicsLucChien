package net.thanhmgz.magicslucchien.System.abilities;

import net.thanhmgz.magicslucchien.MagicsLucChien;
import net.thanhmgz.magicslucchien.System.AttactAbility;
import net.thanhmgz.magicslucchien.System.ItemBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class AB2 extends AttactAbility {
    @Override
    public void run(Location location, Player player, int level) {
        double damage = 0;
        ArmorStand as = location.getWorld().spawn(location.clone().add(0,1,0),ArmorStand.class);
        as.setGravity(false);
        as.setVisible(false);
        as.setItem(EquipmentSlot.HAND,new ItemBuilder(Material.IRON_AXE).build());
        Vector v = player.getLocation().getDirection();
        new BukkitRunnable() {
            double p = Math.PI * 2 / 8;
            int i = 1;
            double d = 0.5;
            @Override
            public void run() {
                EulerAngle eulerAngle = new EulerAngle(p * i,0,0);
                as.setRightArmPose(eulerAngle);
                if (i < 8)
                    i++;
                else
                    i = 1;
                Vector nv = v.normalize().multiply(d);
                d += 0.5;
                if (getNearsByCAEntities(0.3,location.clone().add(nv))) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {

                        }
                    }.runTaskTimer(MagicsLucChien.getInstance(),0,1);
                    cancel();
                }
            }
        }.runTaskTimer(MagicsLucChien.getInstance(),0,2);
    }

    @Override
    public String name() {
        return "Rìu Tia Chóp";
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
