package com.example.lorin.thermontanks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by lorin on 9/14/17.
 */

public class MainThread {
    CanvasEntry callerThread;

    final int WIDTH = 1600;
    final int HEIGHT = 900;

    int canvasXSize;
    int canvasYSize;

    public Camera camera;
    public Map map;
    Tank mainTank;
    ControlStick controlStick;
    Paint paint;
    ImageView canvasObj;
    TextView fpsCounter;
    Multiplayer multiplayer;

    MainThread(CanvasEntry callerThread, ImageView imageView, TextView fpsCounter) { //CanvasEntry callerThread
        this.callerThread = callerThread;
        this.fpsCounter = fpsCounter;
        canvasObj = imageView;


        //Load Objects
        camera = new Camera();
        map = new Map(callerThread, camera);
        mainTank = new Tank(callerThread, camera);
        multiplayer = new Multiplayer(callerThread, camera);


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
            mainTank.move();
            camera.updatePosition();
            multiplayer.run();


            //Draw elements on screen
            callerThread.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    draw();
                }
            });


            //60 fps
            try {
                Thread.sleep((int) (1.0 / 60.0 * 1000.0));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

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
        //drawTank(tempCanvas, mainTank);
        map.draw(tempCanvas);
        mainTank.draw(tempCanvas);
        multiplayer.draw(tempCanvas);
        controlStick.draw(tempCanvas);


        //set bitmap to imageView
        canvasObj.setImageBitmap(tempBitmap);
    }

}
