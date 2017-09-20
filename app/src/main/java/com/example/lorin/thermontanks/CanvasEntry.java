package com.example.lorin.thermontanks;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.graphics.Canvas;

public class CanvasEntry extends AppCompatActivity {

    //ImageView canvasObj;
    //Tank tank;
    MainThread mainThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        ImageView canvasObj = (ImageView) findViewById(R.id.ImageView);
        mainThread = new MainThread(this, canvasObj);


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
}

