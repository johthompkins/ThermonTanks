package com.example.lorin.thermontanks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Respawn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respawn);
    }


    public void quitButton(View view) {
        Intent intent = new Intent(Respawn.this,TankCustom.class);
        finish();
        startActivity(intent);
        //System.exit(0);
    }

    @Override
    public void onBackPressed() {
    }

}
