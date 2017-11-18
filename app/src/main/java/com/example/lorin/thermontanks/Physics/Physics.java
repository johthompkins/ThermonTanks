package com.example.lorin.thermontanks.Physics;

import android.util.Log;

import com.example.lorin.thermontanks.Vector2;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * Created by lorinhersh on 10/11/17.
 * Controls the physics of the client
 *
 * If released ever, this should run server-side so people can't cheat.
 */

public class Physics {
    static private Physics physicsContainer;
    private ArrayList<Rectangle> collisionObjects;
    private Stack<Rectangle> removeObjects;
    private Stack<Rectangle> addObjects;

    private Physics() {
        collisionObjects = new ArrayList<Rectangle>();
        removeObjects = new Stack<>();
        addObjects = new Stack<>();
    }

    public void addPhysicsObject(Rectangle rect) {
        addObjects.add(rect);
    }

    public void removePhysicsObject(Rectangle rect) {
        removeObjects.push(rect);
    }

    private void runAdd() {
        while (!addObjects.empty()) {
            collisionObjects.add(addObjects.pop());
        }
    }

    private void runRemove() {
        while (!removeObjects.isEmpty()) {
            Rectangle obj = removeObjects.pop();
            if (collisionObjects.contains(obj)) {
                collisionObjects.remove(obj);
            } else {
                Log.e("Lorin","Tried to remove object that doesn't exist!");
            }
        }
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
        runRemove();
        runAdd();
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
        //Log.e("Lorin","There are: " + collisionObjects.size());
    }

    private boolean checkOverlap(Rectangle rect1,Rectangle rect2) {
        return  checkOverlapPoint(rect1.getMinPoint(), rect2) || checkOverlapPoint(rect1.getMaxPoint(), rect2) ||
                checkOverlapPoint(rect2.getMinPoint(), rect1) || checkOverlapPoint(rect2.getMaxPoint(), rect1) ||
                checkOverlapPoint(rect2.getMinMaxPoint(), rect1) || checkOverlapPoint(rect2.getMaxMinPoint(), rect1);
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
