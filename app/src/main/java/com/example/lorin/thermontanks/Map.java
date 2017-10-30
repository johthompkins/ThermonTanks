package com.example.lorin.thermontanks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.lorin.thermontanks.Physics.Physics;
import com.example.lorin.thermontanks.Physics.Rectangle;

/**
 * Created by lorin on 9/15/2017.
 * Map background of the game
 */

public class Map {

    private Vector2 position;
    private Bitmap image;
    private Context context;
    private Camera camera;

    int lqmap = R.drawable.lqmap;
    int hqmap = R.drawable.examplemap;

    public Map(Context context, Camera camera) {
        position = new Vector2(0,0);
        this.context = context;
        this.camera = camera;
        image = BitmapFactory.decodeResource(context.getResources(), lqmap);
        image = Bitmap.createScaledBitmap(image,3600,3600, false);

        Physics physicsEngine = Physics.getPhysics();
        Rectangle topWall = new Rectangle(new Vector2(0,0), new Vector2(3600,800),true);
        physicsEngine.addPhysicsObject(topWall);
        Rectangle bottomWall = new Rectangle(new Vector2(0,2800), new Vector2(3600,3600),true);
        physicsEngine.addPhysicsObject(bottomWall);
        Rectangle leftWall = new Rectangle(new Vector2(2800,0), new Vector2(3600,3600),true);
        physicsEngine.addPhysicsObject(leftWall);
        Rectangle rightWall = new Rectangle(new Vector2(0,0), new Vector2(800,3600),true); //left wall
        physicsEngine.addPhysicsObject(rightWall);
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, position.x - camera.getPosition().x, position.y - camera.getPosition().y, null);
    }

    public void move(Vector2 vec) {
        this.position = this.position.add(vec);
    }
}
