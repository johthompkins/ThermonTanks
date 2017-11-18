package com.example.lorin.thermontanks.Tank;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import com.example.lorin.thermontanks.Camera;
import com.example.lorin.thermontanks.Physics.Physics;
import com.example.lorin.thermontanks.Physics.TankRectangle;
import com.example.lorin.thermontanks.R;
import com.example.lorin.thermontanks.Vector2;
import com.example.lorin.thermontanks.Multiplayer.TankPacket;

/**
 * Created by lorin on 9/14/17.
 * Main Tank object of the game
 * used for ever tank
 */

public class Tank {

    private Vector2 pos = new Vector2(1800,1800);
    private Vector2 velocity = new Vector2(0,1);
    private Vector2 size = new Vector2(150, 150);
    private Vector2 rotOffset = new Vector2(150,150);
    private Camera camera;
    private int tankSpeed = 500; //Variable needs to be used with John Thompkins
    private Vector2 facingDir;
    private long lastShoot = 0;

    private Bitmap tankImage;
    private Bitmap rotTankImage;
    private TankApperance tankApperance;
    private Context context;

    private int phyisicsId;
    private TankRectangle tankPhysics;

    //Constructor
    public Tank(Context context, Camera camera, TankApperance tankApperance) {

        //Set default values
        this.context = context;
        this.camera = camera;
        this.tankApperance = tankApperance;

        //Load tank image/size
        tankImage = tankApperance.GetTankDrawable(context);
        size = tankApperance.getVectorSize();
        tankImage = Bitmap.createScaledBitmap(tankImage,(int) size.x,(int) size.y, false);
        rotTankImage = tankImage;

        //Load Physics Engine
        Physics physicsEngine = Physics.getPhysics();
        tankPhysics = new TankRectangle(this, pos, size, true);
        physicsEngine.addPhysicsObject(tankPhysics);
    }

    //Set unit vector velocity (direction)
    public void setDirection(Vector2 dir) {
        this.velocity = dir.multiply(tankSpeed);
    }

    //User triggered shotting
    public void shoot(BulletContainer bullets) {
        if (lastShoot + tankApperance.getShootingDelay() < System.nanoTime()) {
            lastShoot = System.nanoTime();
            Vector2 pos = getPosition();
            Vector2 vel = getDirection();
            int speed = 50;
            bullets.newBullet(pos, vel, speed);
        }
    }


    //Move tank
    public void move(double delta) {
        if (velocity.magnitude(new Vector2(0,0)) > 0) {

            //Move the tank
            this.pos = this.pos.add(velocity.multiply(delta));
            facingDir =this.velocity.divide((float) this.velocity.magnitude(new Vector2(0,0)) );

            updateRotation();

            tankPhysics.updatePosition(pos);
        }
    }

    //Calculate rotation of the tank based on velocity.
    protected void updateRotation() {
        //Rotate the tank in the direction of movement
        Matrix rotMat = new Matrix();
        float rotation = (float) this.velocity.getLookRot() + 90;
        rotMat.postRotate( rotation, 75, 75);
        //Log.e("Lorin","The size of the object was: " + size.x + " " + size.y);
        rotTankImage = Bitmap.createBitmap(tankImage, 0, 0,(int) 75,(int) 75, rotMat, true);

        //Calculate displacement of image based on rotation
        rotation = (rotation)%90+225;
        rotOffset.x = (float) (
                (float) Math.sin(rotation/180*Math.PI) * (float) Math.pow(Math.pow(size.x/2,2)+Math.pow(size.y/2,2),.5)
        );

        rotOffset.y = rotOffset.x;
    }

    //Force set position
    protected void setPosition(Vector2 vector) {
        this.pos = vector;
    }

    //Returns multiplayer version of the tank.
    public TankPacket getMultiplayerInstance() {
        TankPacket tankPacket = new TankPacket();
        tankPacket.size = getSize();
        tankPacket.color = getColor();
        tankPacket.position = getPosition();
        tankPacket.velocity = velocity;
        return tankPacket;
    }

    //get position
    public Vector2 getPosition() {
        return this.pos;
    }

    //Get direction tank is facing (unit vector)
    public Vector2 getDirection() {
        return facingDir;
    }

    //Call back from physics, moves tank to it's last position
    public void collision(Vector2 oldPosition) {
        this.pos = oldPosition;
    }


    //draw the tank on canvas
    public void draw(Canvas canvas) {
        canvas.drawBitmap(rotTankImage, pos.x - camera.getPosition().x + rotOffset.x, pos.y - camera.getPosition().y + rotOffset.y, null);
    }

    //Get the size of the tank
    public String getSize() {
        return this.tankApperance.getSize();
    }

    //Get the color of the tank
    public String getColor() {
        return this.tankApperance.getColor();
    }
}
