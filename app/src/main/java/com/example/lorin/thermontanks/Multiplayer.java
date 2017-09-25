package com.example.lorin.thermontanks;


import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by lorinhersh on 9/20/17.
 */

public class Multiplayer {

    Context context;
    Camera camera;

    Vector2[] players;
    Tank[] tanks = new Tank[5];
    boolean responded = true;
    RequestQueue queue;

    int requestDelay = 0;

    String SERVERADDRESS = "http://Lund.ad.mvnu.edu:8080";

    public Multiplayer(Context context, Camera camera) {
        this.context = context;
        this.camera = camera;
        queue = Volley.newRequestQueue(this.context);
    }

    public void run() {
        if (responded) {
            requestDelay = (requestDelay + 1) % 10;
            if (requestDelay == 0) {
                responded = true;
                StringRequest stringRequest = new StringRequest(Request.Method.GET, SERVERADDRESS,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                responded = true;
                                parseResponse(response);
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Lorin", "Error trying to communicate with server!");
                    }
                });
                stringRequest.setShouldCache(false);
                queue.add(stringRequest);
            }
        }
    }


    public void parseResponse(String response) {
        String[] fullPositions = response.split("&");
        players = new Vector2[fullPositions.length];

        int counter = 0;
        for ( String full : fullPositions) {
            String[] stringVector = full.split(",");
            players[counter] = new Vector2(Integer.parseInt(stringVector[0]),Integer.parseInt(stringVector[1]));
        }
    }


    public void draw(Canvas canvas) {
        if (players != null) {
            int counter = 0;
            for (Vector2 player : players) {
                Tank curTank = tanks[counter];
                if (curTank == null) {
                    tanks[counter] = new Tank(context, camera);
                    curTank = tanks[counter];
                }

                curTank.setPosition(player);
                curTank.draw(canvas);
                counter++;
            }
        }
    }
}
