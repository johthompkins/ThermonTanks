package com.example.lorin.thermontanks;

import com.example.lorin.thermontanks.Tank.Tank;

/**
 * Created by lorin on 9/17/2017.
 * Camera Object to position the camera on the map.
 */

public class Camera {

    private Vector2 position;
    private Tank focus;

    private Vector2 lowerBoundry = new Vector2(700,350);
    private Vector2 upperBoundry = new Vector2(900,550);

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
