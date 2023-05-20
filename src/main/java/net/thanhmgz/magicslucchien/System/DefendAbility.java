package net.thanhmgz.magicslucchien.System;

import net.thanhmgz.magicslucchien.MagicsLucChien;

public abstract class DefendAbility extends Ability {

    public abstract double chance();

    public DefendAbility() {
        MagicsLucChien.getInstance().getDefendAbilityMap().put(name(),this);
    }

}
