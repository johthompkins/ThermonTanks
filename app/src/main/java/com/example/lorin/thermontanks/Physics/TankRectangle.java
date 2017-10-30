package com.example.lorin.thermontanks.Physics;

import android.util.Log;

import com.example.lorin.thermontanks.Tank.Tank;
import com.example.lorin.thermontanks.Vector2;

/**
 * Created by Lorin on 10/22/2017.
 */

public class TankRectangle extends Rectangle {
    private Vector2 position;
    private Vector2 size;

    public Tank callBack;

    private Vector2 lastPoint;

    public TankRectangle(Tank callBack, Vector2 position, Vector2 size, boolean mainTank) {
        this.position = position;
        this.size = size;
        this.setStaticObject(!mainTank);
        calculateVertices();
        this.callBack = callBack;
    }

    public void updatePosition(Vector2 position) {
        lastPoint = this.position;
        this.position = position;
        calculateVertices();
    }

    @Override
    public void undoMovement() {

        //Fix queue bug and overwrite last 2 entries;
        Vector2 origin = new Vector2(lastPoint);
        updatePosition(origin);
        updatePosition(origin);
        callBack.collision(origin);
    }

    private void calculateVertices() {
        setMinPoint(position.subtract(size.divide(2f)));
        setMaxPoint(position.add(size.divide(2f)));
    }
}
