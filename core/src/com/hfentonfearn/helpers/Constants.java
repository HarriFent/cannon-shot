package com.hfentonfearn.helpers;

public class Constants {
    public static final float TIME_STEP = 1/60f;
    public static final int VELOCITY_ITERATIONS = 6;
    public static final int POSITION_ITERATIONS = 2;

    public static final float PPM = 100; // PPM = Pixel perMeter
    public static final float MPP = 1 / PPM; // MPP = Meter per Pixel

    public static final int WORLD_PIXEL_WIDTH = 1200;
    public static final int WORLD_PIXEL_HEIGHT = 800;

    public static final float VELOCITY_DRIFT = 0.9f;
    public static final float VELOCITY_IMPULSE = 0.3f;
    public static final float VELOCITY_MAXVEL = 5f;

}
