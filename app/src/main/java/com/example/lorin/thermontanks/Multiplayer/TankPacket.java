package com.example.lorin.thermontanks.Multiplayer;

import com.example.lorin.thermontanks.Vector2;

import java.io.Serializable;

/**
 * Created by lorinhersh on 10/21/17.
 * A Holder to hold the data for a tank
 * Just the stuff the server needs to know about this tank.
 */

public class TankPacket implements Serializable {

    public Vector2 position;
    public Vector2 velocity;
    public String size;
    public String color;

    public TankPacket() {

    }

    public Vector2 getPosition() {
        return position;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
