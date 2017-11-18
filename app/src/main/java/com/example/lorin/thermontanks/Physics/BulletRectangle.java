package com.example.lorin.thermontanks.Physics;

import android.util.Log;

import com.example.lorin.thermontanks.Tank.Bullet;
import com.example.lorin.thermontanks.Tank.BulletContainer;
import com.example.lorin.thermontanks.Vector2;

/**
 * Created by lorin on 11/13/2017.
 */

public class BulletRectangle extends Rectangle {
    private Vector2 position;
    private Vector2 size = new Vector2(6,6);

    private BulletContainer callBack;
    private Bullet bullet;

    //private Vector2 lastPoint;

    public BulletRectangle(BulletContainer callBack, Vector2 position, Bullet bullet) {
        this.position = position;
        this.setStaticObject(false);
        this.callBack = callBack;
        this.bullet = bullet;
        calculateVertices();
    }

    public void updatePosition(Vector2 position) {
        //lastPoint = this.position;
        this.position = position;
        calculateVertices();
    }

    @Override
    public void undoMovement() {
        //Log.e("Lorin","There was a bullet collision!");
        callBack.collision(bullet);
    }

    private void calculateVertices() {
        setMinPoint(position.subtract(size.divide(2f)));
        setMaxPoint(position.add(size.divide(2f)));
    }
}
