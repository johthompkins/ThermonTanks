package com.example.lorin.thermontanks.Multiplayer;

import com.example.lorin.thermontanks.Tank.Tank;
import com.example.lorin.thermontanks.Vector2;

/**
 * Created by lorinhersh on 10/21/17.
 * A Holder to hold the data for a tank
 * Just the stuff the server needs to know about this tank.
 */

public class TankPacket {

    public Vector2 position;
    public Vector2 velocity;
    public String size;
    public String color;

    public TankPacket() {

    }

    public TankPacket(Tank tank) {
        this.size = tank.getSize();
        this.color = tank.getColor();
        this.position = tank.getPosition();
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
