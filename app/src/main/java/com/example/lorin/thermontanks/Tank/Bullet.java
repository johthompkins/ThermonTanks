package com.example.lorin.thermontanks.Tank;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.lorin.thermontanks.Camera;
import com.example.lorin.thermontanks.Multiplayer.BulletMultiplayer;
import com.example.lorin.thermontanks.Physics.BulletRectangle;
import com.example.lorin.thermontanks.Vector2;

public class Bullet {
    Vector2 position;
    Vector2 velocity;
    int speed;

    BulletRectangle bulletRectangle;

    int runTime;
    private final int FINALRUNTIME = 60;

    public Bullet(Vector2 position, Vector2 velocity, int speed) {
        this.position = position;
        this.velocity = velocity;
        this.speed = speed;
        runTime = 0;
    }

    public void setBulletRectangle(BulletRectangle bulletRectangle) {
        this.bulletRectangle = bulletRectangle;
    }

    public boolean move() {
        this.position = this.position.add(this.velocity.multiply(this.speed));
        bulletRectangle.updatePosition(position);
        runTime++;
        return runTime < FINALRUNTIME;
    }

    public BulletMultiplayer getMultiplayerInstance() {
        BulletMultiplayer bullet = new BulletMultiplayer(this.position,this.velocity,this.speed);
        return bullet;
    }

    public void draw(Canvas canvas, Camera camera, Bitmap bulletImage) {
        canvas.drawBitmap(bulletImage, position.x - camera.getPosition().x, position.y - camera.getPosition().y, null);
    }

}
