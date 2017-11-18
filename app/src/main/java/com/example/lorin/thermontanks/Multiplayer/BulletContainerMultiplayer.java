package com.example.lorin.thermontanks.Multiplayer;


import com.example.lorin.thermontanks.Tank.Bullet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lorinhersh on 10/23/17.
 * This class is replica of BulletContainer, but without added stuff other clients don't need.
 */

public class BulletContainerMultiplayer implements Serializable {
    private ArrayList<BulletMultiplayer> bullets;

    public BulletContainerMultiplayer() {
        bullets = new ArrayList<BulletMultiplayer>();
    }

    public void addBullet(BulletMultiplayer bullet) {
        this.bullets.add(bullet);
    }

    public ArrayList<BulletMultiplayer> getBullets() {
        if (this.bullets == null)
            this.bullets =  new ArrayList<BulletMultiplayer>();
        return this.bullets;
    }
}



