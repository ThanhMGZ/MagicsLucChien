package net.thanhmgz.magicslucchien.System;

import net.minecraft.server.v1_16_R3.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_16_R3.ParticleParam;
import net.minecraft.server.v1_16_R3.ParticleType;
import net.minecraft.server.v1_16_R3.Particles;
import net.thanhmgz.magicslucchien.MagicsLucChien;
import org.bukkit.Particle;

public abstract class AttactAbility extends Ability {


    public AttactAbility() {
        MagicsLucChien.getInstance().getAttactAbilityMap().put(name(),this);

    }


}
