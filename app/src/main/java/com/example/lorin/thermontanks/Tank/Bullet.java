package com.example.lorin.thermontanks.Tank;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.lorin.thermontanks.Camera;
import com.example.lorin.thermontanks.Vector2;

/**
 * Created by lorinhersh on 10/23/17.
 */

public class Bullet {
    Vector2 position;
    Vector2 velocity;
    int speed;
    Bitmap bulletImage;
    Camera camera;

    public Bullet(Vector2 position, Vector2 velocity, int speed, Camera camera) {
        this.position = position;
        this.velocity = velocity;
        this.speed = speed;
        this.camera = camera;

        bulletImage = Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bulletImage);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        tempCanvas.drawRect(0f, 0f, 5f, 5f, paint);

    }

    public void update(Vector2 position, Vector2 velocity, int speed) {
        this.position = position;
        this.velocity = velocity;
        this.speed = speed;
    }

    public void move() {
        this.position = this.position.add(this.velocity.multiply(this.speed));
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bulletImage, position.x - camera.getPosition().x, position.y - camera.getPosition().y, null);
    }
}
