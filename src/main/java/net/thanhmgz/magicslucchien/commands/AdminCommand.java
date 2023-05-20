package net.thanhmgz.magicslucchien.commands;

import net.minecraft.server.v1_16_R3.*;
import net.thanhmgz.magicslucchien.MagicsLucChien;
import net.thanhmgz.magicslucchien.System.Ability;
import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.block.data.CraftBlockData;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.*;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        Location originLoc = player.getLocation();
        if (player.hasPermission("op")) {
            if (strings.length == 1) {
                if (strings[0].equalsIgnoreCase("abilities")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ChatColor.GREEN);
                    for (String ss : MagicsLucChien.getInstance().getAbilityMap().keySet()) {
                        sb.append(ss).append(ChatColor.GRAY).append( " , ").append(ChatColor.GREEN);
                    }
                    player.sendMessage(sb.toString());
                    return true;
                } else if (MagicsLucChien.getInstance().getAbilityMap().containsKey(strings[0])) {
                    MagicsLucChien.getInstance().getAbilityMap().get(strings[0]).run(player.getLocation(),player,1);
                    return true;
                } else if (strings[0].equalsIgnoreCase("dcmlag")) {
                    for (LivingEntity le : player.getWorld().getLivingEntities()) {
                        le.damage(6969);
                    }
                } else if (strings[0].equalsIgnoreCase("test")) {
//                    new BukkitRunnable() {
//                        Location ot1,ot2,ot3,tg1,tg2,tg3,tg4;
//                        double pp = Math.PI / 64;
//                        double d = 0;
//                        @Override
//                        public void run() {
//                            //circle 6x
//                            for (int i = 0; i < 128; i++) {
//                                double x = MathHelper.cos((float) (Math.PI / 128 * (i + 1))) * 4;
//                                double z = MathHelper.sin((float) (Math.PI / 128 * (i + 1))) * 4;
//                                Ability.sendParticle(Color.AQUA, originLoc.clone().add(x, 0, z), 0, null, 0.5f);
//                                Ability.sendParticle(Color.AQUA, originLoc.clone().subtract(x, 0, z), 0, null, 0.5f);
//                            }
//                            for (int i = 0; i < 64; i++) {
//                                double x = MathHelper.cos((float) (Math.PI / 64 * (i + 1))) * 2.4;
//                                double z = MathHelper.sin((float) (Math.PI / 64 * (i + 1))) * 2.4;
//                                Ability.sendParticle(Color.AQUA, originLoc.clone().add(x, 0, z), 0, null, 0.5f);
//                                Ability.sendParticle(Color.AQUA, originLoc.clone().subtract(x, 0, z), 0, null, 0.5f);
//                            }
//                            //out triangle
//                            for (int i = 0; i < 3; i++) {
//                                double x = Math.cos((Math.PI * 2 / 3 * i) + (pp * d % (Math.PI * 2))) * 5;
//                                double z = Math.sin((Math.PI * 2 / 3 * i) + (pp * d % (Math.PI * 2))) * 5;
//                                Location l = originLoc.clone().add(x,0,z);
//                                Ability.sendParticle(Color.ORANGE,l,0,null,0.8f);
//                                if (i == 0) ot1 = l;
//                                else if (i == 1) ot2 = l;
//                                else ot3 = l;
//                            }
//                            Ability.drawLine(ot1,ot2,Color.ORANGE,0.8F);
//                            Ability.drawLine(ot1,ot3,Color.ORANGE,0.8F);
//                            Ability.drawLine(ot2,ot1,Color.ORANGE,0.8F);
//                            Ability.drawLine(ot2,ot3,Color.ORANGE,0.8F);
//                            Ability.drawLine(ot3,ot1,Color.ORANGE,0.8F);
//                            Ability.drawLine(ot3,ot2,Color.ORANGE,0.8F);
//                            for (int i = 0; i < 4; i++) {
//                                double x = Math.cos((Math.PI * 2 / 4 * i) - (pp * d % (Math.PI * 2))) * 4;
//                                double z = Math.sin((Math.PI * 2 / 4 * i) - (pp * d % (Math.PI * 2))) * 4;
//                                Location l = originLoc.clone().subtract(x,0,z);
//                                Ability.sendParticle(Color.YELLOW,l,0,null,0.8f);
//                                if (i == 0) tg1 = l;
//                                else if (i == 1) tg2 = l;
//                                else if (i == 2) tg3 = l;
//                                else tg4 = l;
//                            }
//                            Ability.drawLine(tg1,tg2,Color.YELLOW,0.8F);
//                            Ability.drawLine(tg2,tg3,Color.YELLOW,0.8F);
//                            Ability.drawLine(tg3,tg4,Color.YELLOW,0.8F);
//                            Ability.drawLine(tg4,tg1,Color.YELLOW,0.8F);
//                            for (int i = 0; i < 4; i++) {
//                                double x = Math.cos((Math.PI * 2 / 4 * i) - (pp * d % (Math.PI * 2))) * 1.5;
//                                double z = Math.sin((Math.PI * 2 / 4 * i) - (pp * d % (Math.PI * 2))) * 1.5;
//                                Location l = originLoc.clone().add(x,0,z);
//                                for (int j = 0; j < 16; j++) {
//                                    double xx = MathHelper.cos((float) (Math.PI / 16 * (j + 1))) * 1;
//                                    double zz = MathHelper.sin((float) (Math.PI / 16 * (j + 1))) * 1;
//                                    Ability.sendParticle(Color.AQUA, l.clone().add(xx, 0, zz), 0, null, 0.5f);
//                                    Ability.sendParticle(Color.AQUA, l.clone().subtract(xx, 0, zz), 0, null, 0.5f);
//                                }
//                            }
//                            d+= 0.5;
//                        }
//                    }.runTaskTimer(MagicsLucChien.getInstance(),0,0);
//                    Firework fw = player.getWorld().spawn(originLoc,Firework.class);
//                    FireworkMeta wdm = fw.getFireworkMeta();
//                    wdm.setPower(0);
//                    FireworkEffect.Builder builder = FireworkEffect.builder();
//                    builder.withColor(Color.ORANGE);
//                    builder.with(FireworkEffect.Type.BALL);
//                    wdm.addEffect(builder.build());
//                    fw.setFireworkMeta(wdm);
//                    fw.detonate();
//                    PacketPlayOutWorldParticles packet =new PacketPlayOutWorldParticles(
//                            Particles.FLAME,true,player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ(),
//                            0.1f,0.1f,0.5f,
//                            0.1f,0);
//                    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
//                    player.setVelocity();
                   // shootParticle(player,Particle.FLAME,0.1);
//                    new BukkitRunnable() {
//                        @Override
//                        public void run() {
//                            for (double i = Math.PI / 60; i < Math.PI * 2; i+= Math.PI / 60) {
//                                double x = Math.cos(i) * 20;
//                                double z = Math.sin(i) * 20;
//                                PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
//                                        Particles.FLAME, true, originLoc.getX() + x, originLoc.getY(), originLoc.getZ() + z,
//                                        0,0,0,
//                                        0.1f, 0);
//                                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
//                            }
//                    player.sendMessage(String.valueOf(player.getLocation().distance(originLoc)));
//                        }
//                    }.runTaskTimer(MagicsLucChien.getInstance(),0,0);
                }
            } else if (strings.length == 2) {
                try {
                    if (MagicsLucChien.getInstance().getAbilityMap().containsKey(strings[0])) {
                        MagicsLucChien.getInstance().getAbilityMap().get(strings[0]).run(player.getLocation(), player, Integer.parseInt(strings[1]));
                        return true;

                    }
                } catch (Exception e) {e.printStackTrace();
                    player.sendMessage("Ability ko tồn tại, xem lại các chữ viết hoa và trính tã hoặc chỉ số level");
                }
            } else if (strings.length == 0) {
                player.sendMessage("- /mlca abilities: show tên các ability");
                player.sendMessage("- /mlca {name}: test ability");
                player.sendMessage("- /mlca {name} {level}: test ability theo level");
            }

        }
        return true;

    }
    public void setVelocity(Vector v,int id) {
        Vec3D vd = new Vec3D(v.getX(), v.getY(), v.getZ());
        PacketPlayOutEntityVelocity packet = new PacketPlayOutEntityVelocity(id, vd);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }
    public void shootParticle(Player player, Particle particle, double velocity) {
        Location location = player.getEyeLocation();
        Vector direction = location.getDirection();
        player.getWorld().spawnParticle(particle, location.getX(), location.getY(), location.getZ(), 0, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(),velocity , null);
    }

    public List<String> parabola() {
        List<String> list = new ArrayList<>();
        double j = 1;
        for (int i = 10; i >= 0; i--) {
            double y = 2 - (2 / (j / 10));
            list.add(i + "," + y);
            j++;
        }
        for (String s : list) {
          //  list.add(-Double.parseDouble(s.split(",")[0]) + "," + -Double.parseDouble(s.split(",")[1]));
        }
        return list;
    }


}