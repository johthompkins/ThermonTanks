package com.example.lorin.thermontanks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lorin.thermontanks.Multiplayer.Multiplayer;
import com.example.lorin.thermontanks.Physics.Physics;
import com.example.lorin.thermontanks.Tank.Bullet;
import com.example.lorin.thermontanks.Tank.Tank;
import com.example.lorin.thermontanks.Tank.TankApperance;

/**
 * Created by lorin on 9/14/17.
 * The main thread of the the program,
 * controls order and running
 */

public class MainThread {
    CanvasEntry callerThread;

    final int WIDTH = 1600;
    final int HEIGHT = 900;

    private int canvasXSize;
    private int canvasYSize;

    private Camera camera;
    private Map map;
    private Tank mainTank;
    private ControlStick controlStick;
    private Paint paint;
    private ImageView canvasObj;
    private TextView fpsCounter;
    private Multiplayer multiplayer;
    private FramesController framesPerSecond;
    private Physics physicsEngine;
    private Bullet bullet; //This should be fixed for multiple bullets

    MainThread(CanvasEntry callerThread, ImageView imageView, TankApperance tankApperance) { //CanvasEntry callerThread
        this.callerThread = callerThread;
        canvasObj = imageView;

        //Load Objects
        camera = new Camera();
        physicsEngine = Physics.getPhysics();
        map = new Map(callerThread, camera);
        mainTank = new Tank(callerThread, camera, tankApperance);
        multiplayer = new Multiplayer(callerThread, camera, mainTank);
        framesPerSecond = new FramesController((TextView) callerThread.findViewById(R.id.fps), (TextView) callerThread.findViewById(R.id.load));
        bullet = new Bullet(new Vector2(-500,-500),new Vector2(0,0), 0, camera);

        //Set focus of objects
        camera.setFocus(mainTank);
        controlStick = new ControlStick(callerThread, mainTank);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
    }

    private void getCanvasSize() {
        canvasXSize = canvasObj.getWidth();
        canvasYSize = canvasObj.getHeight();
    }

    //Main loop of thread
    public void run() {
        while (true) {
            framesPerSecond.setFrame();
            double delta = framesPerSecond.getDeltaTime();
            mainTank.move(delta);
            camera.updatePosition();
            multiplayer.run();
            physicsEngine.checkPhysics();
            bullet.move();


            //Draw elements on screen
            callerThread.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    draw();
                }
            });


            //60 fps
            try {
                Thread.sleep(framesPerSecond.getMilisecondDelay());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Get the touched position on screen
    public void touch(int x, int y) {
        if (canvasXSize == 0) {
            getCanvasSize();
        } else {
            x = (int) (((float) x) / canvasXSize * WIDTH);
            y = (int) (((float) y) / canvasYSize * HEIGHT);
            controlStick.setInput(new Vector2(x,y));
            //Log.e("Lorin", "The position touched was: " + x + " " + y);
        }
    }

    //Get the moved position of the touch on screen
    public void moveTouch(int x, int y) {
        if (canvasXSize == 0) {
            getCanvasSize();
        } else {
            x = (int) (((float) x) / canvasXSize * WIDTH);
            y = (int) (((float) y) / canvasYSize * HEIGHT);
            controlStick.setInput(new Vector2(x,y));
            //Log.e("Lorin", "The position touched was: " + x + " " + y);
        }
    }

    public void shoot() {
        Log.e("Lorin","BANG!");
        Vector2 pos = mainTank.getPosition();
        Vector2 vel = mainTank.getDirection();
        int speed = 50;
        bullet.update(pos, vel, speed);
    }

    //The touch let up
    public void touchUp(int x, int y) {
        if (canvasXSize == 0) {
            getCanvasSize();
        } else {
            x = (int) (((float) x) / canvasXSize * WIDTH);
            y = (int) (((float) y) / canvasYSize * HEIGHT);
            controlStick.stopInput(new Vector2(x,y));
            //Log.e("Lorin", "The position touched was: " + x + " " + y);
        }
    }

    //Call drawing methods
    public void draw() {

        //Create canvas
        Bitmap tempBitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);

        //Draw elements on canvas
        map.draw(tempCanvas);
        mainTank.draw(tempCanvas);
        multiplayer.draw(tempCanvas);
        controlStick.draw(tempCanvas);
        bullet.draw(tempCanvas);


        //set bitmap to imageView
        canvasObj.setImageBitmap(tempBitmap);
        framesPerSecond.draw();
    }

}
