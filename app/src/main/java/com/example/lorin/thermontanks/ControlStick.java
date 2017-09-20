package com.example.lorin.thermontanks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by lorin on 9/15/2017.
 */

public class ControlStick {
    Vector2 position = new Vector2(100,400);
    Vector2 centerOffset = new Vector2(200,250);
    Vector2 lastPosition;
    Context context;
    Bitmap controlStick;
    boolean debounce;

    Tank tank;

    ControlStick(Context context, Tank tank) {
        this.context = context;
        this.tank = tank;
        controlStick = BitmapFactory.decodeResource(context.getResources(), R.drawable.controlstick);
        controlStick = Bitmap.createScaledBitmap(controlStick, 400, 400, false);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(controlStick, position.x, position.y, null);
    }

    public void setInput(Vector2 pos) {
        float distance = pos.magnitude(position.add(centerOffset));
        if (distance < 200 && distance > 0) {
            Vector2 dir = pos.subtract(position.add(centerOffset));
            dir = dir.divide(distance);
            if (distance > 100) {
                distance = 100;
            }

            dir = dir.divide((150-distance+20)/50);
            this.tank.setDirection(dir);
            this.lastPosition = pos;
            this.debounce = true;
        }
    }

    public void stopInput(Vector2 pos) {
        if (lastPosition != null) {
            if (this.debounce == true) {
                this.debounce = false;
            } else {
                float distance = pos.magnitude(lastPosition);
                if (distance < 50) {
                    this.tank.setDirection(new Vector2(0, 0));
                }
            }
        }
    }
}

