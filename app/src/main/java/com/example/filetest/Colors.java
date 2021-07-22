package com.example.filetest;

import android.graphics.Color;

public class Colors {
    public static final int orange = Color.argb(255, 250, 153, 23);
    public static final int yellow = Color.argb(255, 243, 202, 62);
    public static final int red = Color.argb(255, 255, 95, 88);
    public static final int rose = Color.argb(255, 255, 51, 102);
    public static final int green = Color.argb(255, 42, 201, 64);
    public static final int blue = Color.argb(255, 51, 255, 255);


    public static final int orangeR = 250;
    public static final int orangeG = 153;
    public static final int orangeB = 23;

    public static final int yellowR = 243;
    public static final int yellowG = 202;
    public static final int yellowB = 62;

    public static final int redR = 255;
    public static final int redG = 95;
    public static final int redB = 88;

    public static final int roseR = 255;
    public static final int roseG = 51;
    public static final int roseB = 102;

    public static final int greenR = 42;
    public static final int greenG = 201;
    public static final int greenB = 64;

    public static final int blueR = 51;
    public static final int blueG = 255;
    public static final int blueB = 255;

    public static int getOrange (float alpha) {return Color.argb((int) (alpha / 100 * 255), orangeR, orangeG, orangeB);}
    public static int getYellow (float alpha) {return Color.argb((int) (alpha / 100 * 255), yellowR, yellowG, yellowB);}
    public static int getRed (float alpha) {return Color.argb((int) (alpha / 100 * 255), redR, redG, redB);}
    public static int getRose (float alpha) {return Color.argb((int) (alpha / 100 * 255), roseR, roseG, roseB);}
    public static int getGreen (float alpha) {return Color.argb((int) (alpha / 100 * 255), greenR, greenG, greenB);}
    public static int getBlue (float alpha) {return Color.argb((int) (alpha / 100 * 255), blueR, blueG, blueB);}
}
