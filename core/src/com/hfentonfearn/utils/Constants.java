package com.hfentonfearn.utils;

public class Constants {
    public static final CharSequence GAME_TITLE = "Cannon Shot";
    public static boolean DEBUGMODE = true;

    public static final float TIME_STEP = 1/60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    //Physics world conversion
    public static final float PPM = 100; // PPM = Pixel perMeter
    public static final float MPP = 1 / PPM; // MPP = Meter per Pixel

    //Window Size
    public static final int WORLD_PIXEL_WIDTH = 1200;
    public static final int WORLD_PIXEL_HEIGHT = 800;

    //Drift Constant: 0 = No drift, 1 = Very Slippy
    public static final float VELOCITY_DRIFT = 0.96f;

    //Velocity drive and turn limit
    public static final float VELOCITY_MAXDRIVEVEL = 3f;
    //public static final float VELOCITY_MAXDRIVEVEL = DEBUGMODE ? 5f : 3f;
    public static final float VELOCITY_MAXTURNVEL = 2f;

    //Velocity impulse and angle set with player input
    public static final float ACCELERATION_TURN = 0.02f;
    public static final float ACCELERATION_DRIVE = 9f;

    //Deceleration rate
    public static final float VELOCITY_DECELERATION = 0.1f;
}
