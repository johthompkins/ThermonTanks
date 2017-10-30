package com.example.lorin.thermontanks;

import java.io.Serializable;

/**
 * Created by lorin on 9/15/2017.
 * 2D Vector class
 * Holds X and Y and provides basic math elements.
 */

public class Vector2 implements Serializable {
    public float x;
    public float y;

    Vector2() {

    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 vec) {
        this.x = vec.x;
        this.y = vec.y;
    }

    public Vector2(int x, int y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public Vector2 multiply(int mag) {
            float magf = (float) mag;
        return new Vector2(this.x * magf, this.y * magf);
    }

    public Vector2 multiply(double mag) {
        float magf = (float) mag;
        return new Vector2(this.x * magf, this.y * magf);
    }

    public Vector2 divide(float value) {
        return new Vector2(this.x / value, this.y / value);
    }

    public Vector2 add(Vector2 vec) {
        return new Vector2(this.x+vec.x,this.y+vec.y);
    }

    public Vector2 subtract(Vector2 vec) {
        return new Vector2(this.x-vec.x,this.y-vec.y);
    }

    public float magnitude(Vector2 vec) {
        //A^2+B^2=C^2
        return (float) Math.pow( (Math.pow(this.x-vec.x,2) + Math.pow(this.y-vec.y,2)), .5);
    }

    public boolean equals(Vector2 vec) {
        return this.x == vec.x && this.y == vec.y;
    }

    public double getLookRot() {
        double res = Math.toDegrees(Math.atan(this.y/this.x));
        if ( x < 0 ) {
            res += 180;
        }
        return res;
    }

    @Override
    public String toString() {
        return this.x + " " + this.y;
    }
}
