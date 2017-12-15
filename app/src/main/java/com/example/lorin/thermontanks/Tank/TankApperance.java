package com.example.lorin.thermontanks.Tank;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.lorin.thermontanks.R;
import com.example.lorin.thermontanks.Vector2;

import java.io.Serializable;

/**
 * Created by John on 9/25/2017.
 * The tank appearance holder.
 */

public class TankApperance implements Serializable
{
    String size;
    String color;
    int value;

    public TankApperance(String size, String color)
    {
        this.size = size;
        this.value = value;
        this.color = color;
    }

    public Vector2 getVectorSize()
    {
        switch (size)
        {
            case "Small": return  new Vector2(75,75);
            case "Medium": return new Vector2(100,100);
            default: return new Vector2(175,175);
        }

    }

    public int getShootingDelay() {
        switch (size) {
            case "Small": return 1000000000 / 2;
            case "Medium": return 1000000000;
            default: return 1000000000 * 2;
        }
    }

    public String getSize()
    {
        return this.size;
    }

    public String getColor()
    {
        return this.color;
    }

    public Bitmap GetTankDrawable(Context context) {
        switch (getColor())
        {
            case "blue": return BitmapFactory.decodeResource(context.getResources(), R.drawable.bluetank);
            case "red": return BitmapFactory.decodeResource(context.getResources(), R.drawable.redtank);
            default: return BitmapFactory.decodeResource(context.getResources(), R.drawable.greentank);
        }
    }

}
