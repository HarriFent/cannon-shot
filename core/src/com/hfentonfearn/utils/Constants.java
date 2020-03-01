package com.hfentonfearn.utils;

public class Constants {
    public static boolean DEBUGMODE = true;

    public static final float TIME_STEP = 1/60f;
    public static final int VELOCITY_ITERATIONS = 2;
    public static final int POSITION_ITERATIONS = 2;

    //Physics world conversion
    public static final float PPM = 100; // PPM = Pixel perMeter
    public static final float MPP = 1 / PPM; // MPP = Meter per Pixel

    //Window Size
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;

    //Drift Constant: 0 = No drift, 1 = Very Slippy
    public static final float VELOCITY_DRIFT = 0.96f;

    //Velocity drive and turn limit
    //public static final float VELOCITY_MAXDRIVEVEL = 5f;
    public static final float VELOCITY_MAXDRIVEVEL = DEBUGMODE ? 5f : 3f;
    public static final float VELOCITY_MAXTURNVEL = 2f;

    //Velocity impulse and angle set with player input
    public static final float ACCELERATION_TURN = 0.1f;
    public static final float ACCELERATION_DRIVE = 30f;

    //Angular and Linear Damping for player
    public static final float DAMPING_ANGULAR = 2f;
    public static final float DAMPING_LINEAR = 0.5f;

    //Shooting Constants
    public static final float CANNONBALL_DYING_VELOCITY = 1f;


}
