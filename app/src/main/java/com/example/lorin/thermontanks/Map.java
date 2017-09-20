package com.example.lorin.thermontanks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by lorin on 9/15/2017.
 */

public class Map {

    private Vector2 position;
    private Bitmap image;
    private Context context;
    private Camera camera;

    public Map(Context context, Camera camera) {
        position = new Vector2(0,0);
        this.context = context;
        this.camera = camera;
        image = BitmapFactory.decodeResource(context.getResources(), R.drawable.examplemap);
        //image = Bitmap.createScaledBitmap(image,(int) size.x,(int) size.y, false);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, position.x - camera.getPosition().x, position.y - camera.getPosition().y, null);
    }

    public void move(Vector2 vec) {
        this.position = this.position.add(vec);
    }
}
