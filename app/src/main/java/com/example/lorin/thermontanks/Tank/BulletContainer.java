package com.example.lorin.thermontanks.Tank;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.example.lorin.thermontanks.Camera;
import com.example.lorin.thermontanks.Multiplayer.BulletContainerMultiplayer;
import com.example.lorin.thermontanks.Physics.BulletRectangle;
import com.example.lorin.thermontanks.Physics.Physics;
import com.example.lorin.thermontanks.Vector2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lorinhersh on 10/23/17.
 */

public class BulletContainer implements Serializable {
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> removeBullets;
    private transient Camera camera;
    private transient Bitmap bulletImage;
    //boolean moving;

    private boolean addBullet;
    private Bullet tempBullet;
    private BulletRectangle tempRect;

    public BulletContainer(Camera camera) {
        this.bullets = new ArrayList<>();
        this.removeBullets = new ArrayList<>();
        this.camera = camera;
        this.addBullet = false;

        bulletImage = Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bulletImage);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        tempCanvas.drawRect(0f, 0f, 5f, 5f, paint);
    }

    public Bitmap getBulletImage() {
        return bulletImage;
    }

    public void reloadObject(Camera camera) {
        this.addBullet = false;
        this.camera = camera;

        bulletImage = Bitmap.createBitmap(5, 5, Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bulletImage);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        tempCanvas.drawRect(0f, 0f, 5f, 5f, paint);
    }

    public BulletContainerMultiplayer getBulletContainerMultiplayer() {
        BulletContainerMultiplayer bulletContainerMultiplayer = new BulletContainerMultiplayer();

        for (Bullet bullet : bullets) {
            bulletContainerMultiplayer.addBullet(bullet.getMultiplayerInstance());
        }
        return bulletContainerMultiplayer;
    }

    public void newBullet(Vector2 position, Vector2 velocity, int speed) {
        tempBullet = new Bullet(position, velocity, speed);
        BulletRectangle bulletRectangle = new BulletRectangle(this, tempBullet.position, tempBullet);
        tempBullet.setBulletRectangle(bulletRectangle);
        tempRect = bulletRectangle;
        addBullet = true;
    }

    public void move() {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet bullet = it.next();
            if (!bullet.move()) {
                removeBullets.add(bullet);
            }
        }

        Physics physicsEngine = Physics.getPhysics();
        for (Bullet bullet : removeBullets) {
            physicsEngine.removePhysicsObject(bullet.bulletRectangle);
            bullets.remove(bullet);
        }

        if (removeBullets.size() > 0) {
            removeBullets = new ArrayList<>();
        }

        callBack();
    }

    private void callBack() {
        if (addBullet) {
            bullets.add(tempBullet);
            Physics.getPhysics().addPhysicsObject(tempRect);
            addBullet = false;
        }

    }

    public void collision(Bullet bullet) {
        //Bullet hit something
        removeBullets.add(bullet);
    }

    public void draw(Canvas canvas) {
        try {
            Iterator<Bullet> it = bullets.iterator();
            while (it.hasNext()) {
                Bullet bullet = it.next();
                bullet.draw(canvas, camera, bulletImage);
            }
        } catch (Exception e) { //Ignore concurrent modification exception
            //e.printStackTrace();
        }

    }
}

