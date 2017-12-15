package com.example.lorin.thermontanks.Multiplayer;

import java.io.Serializable;

/**
 * Created by lorinhersh on 12/4/17.
 */

public class DamagePacket implements Serializable{

    public String damageTarget = "";

    public DamagePacket() {

    }

    public void setDamageTarget(String damageTarget) {
        this.damageTarget = damageTarget;
    }

    public String getDamageTarget() {
        return damageTarget;
    }
}
