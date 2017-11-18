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

    int lqmap = R.drawable.mqmap;
    int hqmap = R.drawable.examplemap;
    int mqmap = R.drawable.mqmap;
    int slqmap = R.drawable.slqmap;


    Wall[] walls = {new Wall(new Vector2(1937,1026),new Vector2(81,594)),
        new Wall(new Vector2(1937,1557), new Vector2(621,62)),
        new Wall(1579,1935,79,596),
        new Wall(1041,1935,618,62),

            new Wall(2120,2163,64,58),
            new Wall(2237,2161,64,58),
            new Wall(2161,2162,64,58),
            new Wall(2484, 2499, 64, 58),
            new Wall(2600, 2499, 64, 58),
            new Wall(2716, 2497, 64 ,58),
            new Wall(1431, 1397, 64, 58),
            new Wall(1312, 1397, 64, 58),
            new Wall(1197, 1396, 64, 58),
            new Wall(1066, 1063, 64, 58),
            new Wall(950, 1061, 64, 58),
            new Wall(834, 1062, 64, 58),
            new Wall(2355, 2161, 64, 58)
    };

    public Map(Context context, Camera camera) {
        position = new Vector2(0,0);
        this.context = context;
        this.camera = camera;
        image = BitmapFactory.decodeResource(context.getResources(), mqmap);
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

        for (Wall wall : walls) {
            Rectangle newWall = new Rectangle(wall.min,wall.max, true);
            physicsEngine.addPhysicsObject(newWall);
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, position.x - camera.getPosition().x, position.y - camera.getPosition().y, null);
    }

    public void move(Vector2 vec) {
        this.position = this.position.add(vec);
    }
}

class Wall {
    public Vector2 min;
    public Vector2 max;

    Wall(int x1, int y1, int x2, int y2) {
        this.min = new Vector2(x1,y1);
        this.max = new Vector2(x2+x1,y2+y1);
    }

    Wall(Vector2 min, Vector2 max) {
        this.min = min;
        this.max = max.add(min);
    }
}
