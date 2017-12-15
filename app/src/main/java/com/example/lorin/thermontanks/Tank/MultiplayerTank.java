package com.example.lorin.thermontanks.Tank;

import android.content.Context;

import com.example.lorin.thermontanks.Camera;
import com.example.lorin.thermontanks.Multiplayer.Multiplayer;
import com.example.lorin.thermontanks.Physics.BulletPhysics;
import com.example.lorin.thermontanks.Physics.TankRectangle;
import com.example.lorin.thermontanks.Vector2;

/**
 * Created by lorinhersh on 10/2/17.
 * The Tank object for Multiplayer.
 * Allows easy inter/extra-polation of tank between data packets recieved from server
 */

public class MultiplayerTank extends Tank {

    private int stepper;
    private int MAX_STEPS = 18; //Estimated 6 frames between server response 18 frame buffer
    private int VELSTEPS = 18;
    private Vector2 startPos;
    private Vector2 goalPos = new Vector2(0,0);
    private Vector2 velocity = new Vector2(0,0);
    private TankRectangle tankPhysics;
    private String clientId;
    private Multiplayer callback;

    public MultiplayerTank(Context context, Camera camera, TankApperance tankApperance, Vector2 startPos, String clientId, Multiplayer callback) {
        super(context, camera, tankApperance);
        this.startPos = startPos;
        this.clientId = clientId;
        this.callback = callback;
        tankPhysics = new TankRectangle(this, super.getPosition(), tankApperance.getVectorSize(), true);
        BulletPhysics.getPhysics().addPhysicsObject(tankPhysics);
    }

    public void setGoal(Vector2 goal, Vector2 velocity) {
        startPos = this.getPosition();
        this.velocity = velocity;
        goalPos = goal;
        stepper = 0;
        stepPosition();
    }

    public Vector2 getGoalPos() {
        return this.goalPos;
    }

    @Override
    public void collision(Vector2 origin) {
        //Multiplayer tank was hit
        callback.setDamageTarget(clientId);

    }

    public void stepPosition() {
        stepper++;
        //if (stepper <= MAX_STEPS) {
        if (stepper <= VELSTEPS) {
            Vector2 stepVec = goalPos.subtract(startPos).multiply(((float) stepper) / MAX_STEPS);
            setPosition(startPos.add(stepVec));
            setDirection(stepVec);
            updateRotation();
            tankPhysics.updatePosition(startPos.add(stepVec));
        } else {
            setPosition(goalPos);
        }
    }
}
