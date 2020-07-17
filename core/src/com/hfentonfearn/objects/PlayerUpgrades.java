package com.hfentonfearn.objects;

public class PlayerUpgrades {

    public static int speed = 0;
    public static int steering = 0;
    public static int hull = 0;
    public static int cannonFire = 0;
    public static int cannonRange = 0;
    public static int inventory = 0;

    public static void fullUpgrade() {
        speed = 4;
        steering = 4;
        hull = 4;
        cannonFire = 4;
        cannonRange = 4;
        inventory = 4;
    }

}
