package com.example.lorin.thermontanks;

import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by lorinhersh on 9/28/17.
 *Controls the framerate for the engine to the best of its ability
 */

public class FramesController {
    TextView fpsView;
    TextView loadView;

    private long startTime = 0;
    private long endTime = 0;
    private long calcTime = 0;
    private int delay = 0;

    private int drawDelay = 0;
    private double deltaTime = 0;

    private double GOALFPS = 60.0; //30 or 15 instead of 60 for older device support

    ///////////////??^ADD SETTING^???//////////////////////

    public FramesController(TextView fpsView, TextView loadView) {
        this.fpsView = fpsView;
        this.loadView = loadView;
    }

    private long getTick() {
        return System.nanoTime();
    }

    public int getMilisecondDelay() {
        calcTime = getTick();
        if (endTime != 0l) {
            delay = (int)  ( ( (1.0 / GOALFPS) - (calcTime - endTime)/1000000000.0) * 1000.0); // 60/30/15 fps - frameCalc time * 1000 for miliseconds
            if (delay <= 0) {
                delay = 1;
            }
            return delay;
        } else
            return (int)   ((1.0 / GOALFPS) * 1000.0);
    }


    public void setFrame() {
        this.startTime = this.endTime;
        this.endTime = getTick();

        if (this.startTime != 0) {
            deltaTime = ( (endTime - startTime) / 1000000000.0);
        }
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public void draw() {
        drawDelay = (drawDelay+1)%30;
        if (drawDelay == 0) {
            if (endTime != 0l && startTime != 0l) {
                fpsView.setText("FPS: " + (int) Math.floor(1.0 / deltaTime));
                if (delay != 0) {
                    int frameLength = (int) (endTime - startTime);

                    loadView.setText("Load: " + Math.floor(((long) (frameLength - delay * 1000000.0) * 100.0) / ((long) (frameLength))) + "%");
                }
            } else {
                fpsView.setText("0");
            }
        }
    }

}
