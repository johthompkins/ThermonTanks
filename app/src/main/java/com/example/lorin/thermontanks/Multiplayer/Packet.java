package com.example.lorin.thermontanks.Multiplayer;

import com.example.lorin.thermontanks.Vector2;

import java.io.Serializable;

/**
 * Created by lorinhersh on 10/16/17.
 * A data packet send between the server and client to transfer information.
 */

public class Packet implements Serializable {
    public TankPacket selfTankPacket;
    public TankPacket otherTankPackets[];

    public String ClientId;

    public Packet(TankPacket selfTankPacket) {
        this.selfTankPacket = selfTankPacket;
    }

    public Packet() {
        //Do nothing
    }
}
