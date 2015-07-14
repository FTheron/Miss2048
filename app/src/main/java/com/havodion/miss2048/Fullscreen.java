package com.havodion.miss2048;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Fullscreen extends ActionBarActivity {
    private int imgNum = 0;
    private int oldNum = 0;
    private ArrayList<String> colorList = new ArrayList<String>();
    private Random randomGenerator = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        colorList.add("#FF75FF");
        colorList.add("#FF0066");
        colorList.add("#CC99FF");
        colorList.add("#C285FF");
        colorList.add("#B870FF");
        colorList.add("#AD5CFF");
        colorList.add("#A347FF");
        colorList.add("#9933FF");
        colorList.add("#8A2EE6");
        colorList.add("#B2476B");
        colorList.add("#FFCCFF");
        colorList.add("#FF4D94");
        colorList.add("#AD33FF");
        colorList.add("#FFA3FF");

        Timer timer = new Timer();
        TimerTask timerTask;
        timerTask = new TimerTask() {
            @Override
            public void run() {
                // Change the color of the white heart
                changeColor();
            }
        };
        timer.schedule(timerTask, 0, 800);
    }

    public void changeColor(){
        do {
            imgNum = randomGenerator.nextInt(colorList.size()-1);
        }while (imgNum == oldNum);
        oldNum = imgNum;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //stuff that updates ui
                ImageView img = (ImageView) findViewById(R.id.heartImage);
                img.getDrawable().setColorFilter(Color.parseColor(colorList.get(imgNum)) ,PorterDuff.Mode.MULTIPLY);
                img.invalidateDrawable(img.getDrawable());
            }
        });
    }
}
