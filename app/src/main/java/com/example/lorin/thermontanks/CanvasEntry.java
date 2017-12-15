package com.example.lorin.thermontanks;

import android.content.Intent;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;

import com.example.lorin.thermontanks.Tank.TankApperance;

/**
 * Activity
 * The entry into the main game
 * Calls the MainThread.
 */
public class CanvasEntry extends AppCompatActivity {

    //ImageView canvasObj;
    //Tank tank;
    MainThread mainThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_canvas);


        //Intent intent = new Intent(CanvasEntry.this, Respawn.class);
        //startActivity(intent);

        Intent intent = getIntent();
        TankApperance tankApperance = (TankApperance) intent.getSerializableExtra("TankAppereance");

        ImageView canvasObj = (ImageView) findViewById(R.id.ImageView);
        mainThread = new MainThread(this, canvasObj, tankApperance);


        new Thread(new Runnable() {
            @Override
            public void run() {
                mainThread.run();
            }
        }).start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mainThread.touch(x,y);
            case MotionEvent.ACTION_MOVE:
                mainThread.moveTouch(x,y);
            case MotionEvent.ACTION_UP:
                //mainThread.touchUp(x,y);
            case MotionEvent.ACTION_CANCEL:
                mainThread.touchUp(x,y);
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            mainThread.shoot();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
    }
}

