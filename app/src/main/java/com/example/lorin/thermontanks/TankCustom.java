package com.example.lorin.thermontanks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lorin.thermontanks.Tank.TankApperance;

/**
 * Created by John on ??/??/2017
 * Entry into the APP -
 * Lets user choose from a few options and then get into the game
 */

public class TankCustom extends AppCompatActivity
{

    TextView sizeTextView;
    TextView colorTextView;
    int currentSize,currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_custom);

        sizeTextView = (TextView) findViewById(R.id.SizeTextView);
        colorTextView = (TextView) findViewById(R.id.ColorTextView);
        sizeUpdateText();
        colorUpdateText();
    }

    TankApperance[] tank = {
            new TankApperance("Small", "green"),
            new TankApperance("Medium", "blue"),
            new TankApperance("Large", "red")
    };

    ////////////////////////////////////////   Size UI//////////////////////////////////////////////////////////////////////////////////
    public void sizeUpdateText()
    {
        sizeTextView.setText(tank[currentSize].getSize());
    }//end of updateText

    public void sizeNextArrow(View view)
    {
        currentSize = (currentSize + 1) % tank.length;
        sizeUpdateText();
        colorUpdateText();
    }//end of nextArrow

    public void sizePrevArrow(View view)
    {
        if( currentSize == 0)
        {
            currentSize = tank.length - 1;
            sizeUpdateText();
        }
        else
        {
            currentSize = currentSize -1;
            sizeUpdateText();
        }
    }//end of prevArrow

////////////////////////////////////////////////////////   Color UI   //////////////////////////////////////////////////////////////////

    public void colorUpdateText()
    {
        colorTextView.setText(tank[currentColor].getColor());
    }//end of updateText

    public void colorNextArrow(View view)
    {
        currentColor = (currentColor + 1) % tank.length;
        colorUpdateText();
    }//end of nextArrow

    public void colorPrevArrow(View view)
    {
        if( currentColor == 0)
        {
            currentColor = tank.length - 1;
            colorUpdateText();
        }
        else
        {
            currentColor = currentColor -1;
            colorUpdateText();
        }
    }//end of prevArrow

    //////////////////////////////////////     Start Button /////////////////////////////////////////////////////////////
    public void startGame(View view)
    {

        String colorText = (String) colorTextView.getText();
        String sizeText = (String) sizeTextView.getText();

        Intent intent = new Intent(TankCustom.this,CanvasEntry.class);

        TankApperance tankApperance = new TankApperance(sizeText, colorText);
        intent.putExtra("TankAppereance",tankApperance);

        startActivity(intent);
    }


    public void goToEnd(View view) {

    }

    @Override
    public void onBackPressed() {
    }

}
