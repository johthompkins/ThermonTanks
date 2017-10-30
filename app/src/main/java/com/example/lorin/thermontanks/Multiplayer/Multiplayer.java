package com.example.lorin.thermontanks.Multiplayer;


import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;

import com.example.lorin.thermontanks.Camera;
import com.example.lorin.thermontanks.Tank.MultiplayerTank;
import com.example.lorin.thermontanks.Tank.Tank;
import com.example.lorin.thermontanks.Tank.TankApperance;
import com.example.lorin.thermontanks.Vector2;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import android.util.Base64;

/**
 * Created by lorinhersh on 9/20/17.
 * The multiplayer aspect of the game
 * Creates "MultiplayerTanks" that are rendered on screen
 */

public class Multiplayer {

    private Context context;
    private Camera camera;
    private Tank localTank;

    private MultiplayerTank[] tanks = new MultiplayerTank[5]; // Limited to 5 players for now
    private Packet lastPacket;

    //Delay between spamming server
    private boolean responded = true;
    private int requestDelay = 0;
    private final int REQUESTWAIT = 6;

    private final static String SERVERADDRESS = "Lund.ad.mvnu.edu";
    private final static int PORT = 25565;

    public Multiplayer(Context context, Camera camera, Tank localTank) {
        this.context = context;
        this.camera = camera;
        this.localTank = localTank;
    }

    public void run() {
        if (responded) {
            requestDelay = (requestDelay + 1) % REQUESTWAIT;
            if (requestDelay == 0) {
                responded = false; //Don't wait for last response (When = true)

                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendData();
                        lastPacket = getData();
                        responded = true;
                    }
                });
                thread.start();
            }
        }
    }

    public void sendData() {
        try {
            Log.e("Lorin","Trying to connect to the server....");
            Socket socket = new Socket(SERVERADDRESS, PORT);
            Log.e("Lorin","Connected!");
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            Packet newObj = new Packet(new TankPacket(localTank));
            String retString = encodeObject(newObj);
            printWriter.println(retString);
            socket.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public Packet getData() {
        String inputLine;
        Packet packet = null;

        try {
            Log.e("Lorin","Waiting for connection from server...");
            ServerSocket serverSocket = new ServerSocket(PORT);
            Socket clientSocket = serverSocket.accept();
            Log.e("Lorin","Done!");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while((inputLine = bufferedReader.readLine()) != null) {
                packet = decodeObject(inputLine);
            }
            serverSocket.close();

        }
        catch(Exception e) {
            System.out.println(e);
        }

        return packet;
    }

    public String encodeObject(Packet packet) {
        String encodedObj = null;
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(packet);
            so.flush();
            encodedObj = new String(Base64.encode(bo.toByteArray(),Base64.NO_WRAP));
        } catch (Exception e) {
            System.out.println(e);
        }
        return encodedObj;
    }

    public Packet decodeObject(String encodedObj) {
        Packet packet = null;
        try {
            byte b[] = Base64.decode(encodedObj.getBytes(),Base64.DEFAULT);
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            packet = (Packet) si.readObject();
        } catch (Exception e) {
            System.out.println(e);
        }
        return packet;
    }


    public void draw(Canvas canvas) {
        if (lastPacket != null) {
            int counter = 0;
            for (TankPacket tankPacket : lastPacket.otherTankPackets) {
                MultiplayerTank curTank = tanks[counter];
                if (curTank == null) {
                    tanks[counter] = new MultiplayerTank(context, camera, new TankApperance(tankPacket.getSize(),tankPacket.getColor()), tankPacket.getPosition());
                    curTank = tanks[counter];
                }
                if (curTank.getGoalPos().equals(tankPacket.getPosition())) {
                    curTank.stepPosition();
                } else {
                    curTank.setGoal(tankPacket.getPosition());
                }
                curTank.draw(canvas);
                counter++;
            }
        }
    }
}
