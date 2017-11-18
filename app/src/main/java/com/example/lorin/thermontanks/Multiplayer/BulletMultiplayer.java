package com.example.lorin.thermontanks.Multiplayer;


import com.example.lorin.thermontanks.Vector2;

import java.io.Serializable;

/**
 * Created by lorinhersh on 11/8/17.
 * This class is replica of Bullet, but without added stuff other clients don't need.
 */

public class BulletMultiplayer implements Serializable{
    Vector2 position;
    Vector2 velocity;
    int speed;

    int runTime;
    private final int FINALRUNTIME = 60;

    //Constructor
    public BulletMultiplayer(Vector2 position, Vector2 velocity, int speed) {
        this.position = position;
        this.velocity = velocity;
        this.speed = speed;
        runTime = 0;
    }

    public boolean move() {
        this.position = this.position.add(this.velocity.multiply(this.speed));
        runTime++;
        return runTime < FINALRUNTIME;
    }

}
