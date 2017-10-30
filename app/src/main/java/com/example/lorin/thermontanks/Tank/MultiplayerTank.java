package com.example.lorin.thermontanks.Tank;

import android.content.Context;

import com.example.lorin.thermontanks.Camera;
import com.example.lorin.thermontanks.Vector2;

/**
 * Created by lorinhersh on 10/2/17.
 * The Tank object for Multiplayer.
 * Allows easy inter/extra-polation of tank between data packets recieved from server
 */

public class MultiplayerTank extends Tank {

    private int stepper;
    private int MAX_STEPS = 36; //Estimated 6 frames between server response 18 frame buffer
    private Vector2 startPos;
    private Vector2 goalPos = new Vector2(0,0);

    public MultiplayerTank(Context context, Camera camera, TankApperance tankApperance, Vector2 startPos) {
        super(context, camera, tankApperance);
        this.startPos = startPos;
    }

    public void setGoal(Vector2 goal) {
        startPos = this.getPosition();
        goalPos = goal;
        stepper = 0;
        stepPosition();
    }

    public Vector2 getGoalPos() {
        return this.goalPos;
    }

    public void stepPosition() {
        stepper++;
        //if (stepper <= MAX_STEPS) {
            Vector2 stepVec = goalPos.subtract(startPos).multiply(((float) stepper) / MAX_STEPS);
            setPosition(startPos.add(stepVec));
            setDirection(stepVec);
            updateRotation();
        //}
    }
}
