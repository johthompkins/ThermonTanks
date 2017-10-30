package com.example.lorin.thermontanks.Physics;

import android.util.Log;

import com.example.lorin.thermontanks.Vector2;

import java.util.ArrayList;

/**
 * Created by lorinhersh on 10/11/17.
 * Controls the physics of the client
 *
 * If released ever, this should run server-side so people can't cheat.
 */

public class Physics {
    static private Physics physicsContainer;
    private ArrayList<Rectangle> collisionObjects;

    private Physics() {
        collisionObjects = new ArrayList<Rectangle>();
    }

    public void addPhysicsObject(Rectangle rect) {
        collisionObjects.add(rect);
    }

    public static Physics getPhysics() {
        if (physicsContainer == null) {
            physicsContainer = new Physics();
            return physicsContainer;
        } else {
            return physicsContainer;
        }
    }

    public void checkPhysics() {
        for ( Rectangle movingObject : collisionObjects) {
            if (movingObject.getStaticObject() == false) {

                for (Rectangle staticObject : collisionObjects) {
                    if (staticObject.getStaticObject() == true) {
                        if (checkOverlap(movingObject,staticObject)) {

                            movingObject.undoMovement();
                        }
                    }
                }
            }
        }
    }

    private boolean checkOverlap(Rectangle rect1,Rectangle rect2) {
        return  checkOverlapPoint(rect1.getMinPoint(), rect2) || checkOverlapPoint(rect1.getMaxPoint(), rect2);
    }

    private boolean checkOverlapPoint(Vector2 point, Rectangle rect) {
        if ( point.x > rect.getMinPoint().x && point.x < rect.getMaxPoint().x) {
            if ( point.y > rect.getMinPoint().y && point.y < rect.getMaxPoint().y) {
                return true;
            }
        }
        return false;
    }
}
