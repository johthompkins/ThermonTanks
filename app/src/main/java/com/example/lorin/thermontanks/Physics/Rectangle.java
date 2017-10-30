package com.example.lorin.thermontanks.Physics;

import android.content.ReceiverCallNotAllowedException;
import android.os.Build;

import com.example.lorin.thermontanks.Vector2;

/**
 * Created by Lorin on 10/22/2017.
 */

public class Rectangle {
    private Vector2 minPoint;
    private Vector2 maxPoint;
    private boolean staticObject;

    private int id;

    private static int uuid = 0;

    public Rectangle(Vector2 minPoint, Vector2 maxPoint, boolean staticObject) {
        this.minPoint = minPoint;
        this.maxPoint = maxPoint;
        this.staticObject = staticObject;

        id = uuid;
        uuid++;
    }

    public Rectangle(Rectangle rect) {
        this.minPoint = rect.getMinPoint();
        this.maxPoint = rect.getMaxPoint();
        this.staticObject = rect.getStaticObject();
        this.id = rect.getId();
    }

    public Rectangle() {

    }

    public void setMinPoint(Vector2 minPoint) {
        this.minPoint = minPoint;
    }

    public void setMaxPoint(Vector2 maxPoint) {
        this.maxPoint = maxPoint;
    }

    public void setStaticObject(boolean staticObject) {
        this.staticObject = staticObject;
    }

    public int getId() {
        return id;
    }

    public boolean getStaticObject() {
        return staticObject;
    }

    public Vector2 getMinPoint() {
        return minPoint;
    }

    public Vector2 getMaxPoint() {
        return maxPoint;
    }

    public void undoMovement() {
        //Extend only for non-static objects;
    }

}
