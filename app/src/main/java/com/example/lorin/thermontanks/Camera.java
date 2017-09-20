package com.example.lorin.thermontanks;

import android.util.Log;

/**
 * Created by lorin on 9/17/2017.
 */

public class Camera {

    private Vector2 position;
    private Tank focus;

    private Vector2 lowerBoundry = new Vector2(100,100);
    private Vector2 upperBoundry = new Vector2(1350,650);

    public Camera() {
        this.position = new Vector2(0,0);
    }

    public Vector2 getPosition() {
        return new Vector2(position);
    }

    public void updatePosition() {
        Vector2 focusPos = focus.getPosition();
        if ( focusPos.x < position.x + lowerBoundry.x) {
            position.x = focusPos.x - lowerBoundry.x;
        } else if (focusPos.x > position.x + upperBoundry.x) {
            position.x = focusPos.x - upperBoundry.x;
        }


        if ( focusPos.y < position.y + lowerBoundry.y) {
            position.y = focusPos.y - lowerBoundry.y;
        } else if (focusPos.y > position.y + upperBoundry.y) {
            position.y = focusPos.y - upperBoundry.y;
        }
    }

    public void setFocus(Tank focus) {
        this.focus = focus;
    }


}
