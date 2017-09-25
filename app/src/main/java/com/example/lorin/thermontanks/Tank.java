package com.example.lorin.thermontanks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by lorin on 9/14/17.
 */

public class Tank {

    private Vector2 pos;
    private Vector2 velocity;
    private Vector2 size = new Vector2(150, 150);
    private Vector2 rotOffset;
    private Camera camera;
    private int tankSpeed = 5;

    private Bitmap tankImage;
    private Bitmap rotTankImage;
    private Context context;

    Tank(Context context, Camera camera) {
        this.context = context;
        this.camera = camera;
        tankImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.tank);
        tankImage = Bitmap.createScaledBitmap(tankImage,(int) size.x,(int) size.y, false);
        rotTankImage = tankImage;
        this.pos = new Vector2(0,0);
        this.velocity = new Vector2(0,0);
        this.rotOffset = new Vector2(0,0);
    }

    public void setDirection(Vector2 dir) {
        this.velocity = dir.multiply(tankSpeed);
    }

    public void move() {
        if (velocity.magnitude(new Vector2(0,0)) > 0) {
            this.pos = this.pos.add(velocity);
            //Log.e("Lorin", "Moving in the degree direction of: " + velocity.getLookRot());
            Matrix rotMat = new Matrix();
            float rotation = (float) this.velocity.getLookRot() + 90;
            rotMat.postRotate( rotation, 75, 75);
            rotTankImage = Bitmap.createBitmap(tankImage, 0, 0,(int) size.x,(int) size.y, rotMat, true);

            //rotOffset.x = (float) ((((float) Math.sin(45+rotation)) * ((float) Math.pow(Math.pow(size.x,2),.5))));
            rotation = (rotation)%90+225;
            rotOffset.x = (float) (
                    (float) Math.sin(rotation/180*Math.PI) * (float) Math.pow(Math.pow(size.x/2,2)+Math.pow(size.y/2,2),.5)
            );

            rotOffset.y = rotOffset.x;
        }
    }

    public void setPosition(Vector2 vector) {
        this.pos = vector;
    }

    public Vector2 getPosition() {
        return this.pos;
    }


    public void draw(Canvas canvas) {
        canvas.drawBitmap(rotTankImage, pos.x - camera.getPosition().x + rotOffset.x, pos.y - camera.getPosition().y + rotOffset.y, null);
    }
}
