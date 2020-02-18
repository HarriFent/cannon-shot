package com.hfentonfearn.helpers;

public class Constants {
    public static final float TIME_STEP = 1/60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    public static final float PPM = 100; // PPM = Pixel perMeter
    public static final float MPP = 1 / PPM; // MPP = Meter per Pixel

    public static final int WORLD_PIXEL_WIDTH = 1200;
    public static final int WORLD_PIXEL_HEIGHT = 800;

    public static final float VELOCITY_DRIFT = 0.95f;
    public static final float VELOCITY_MAXDRIVEVEL = 5f;
    public static final float VELOCITY_MAXTURNVEL = 2f;
    public static final float VELOCITY_TURN = 0.02f;
    public static final float VELOCITY_DRIVE = 9f;
    public static final float VELOCITY_DECELERATION = 0.1f;

    public static boolean DEBUGMODE = false;
}
