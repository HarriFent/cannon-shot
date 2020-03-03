package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

import static com.hfentonfearn.utils.Constants.*;

public class ShipStatisticComponent implements Component {

    private float speed;
    private float steering;
    private float hull;
    private int firerate;
    private float firerange;
    private float inventorySize;
    public boolean changed;

    public ShipStatisticComponent() {
        speed = DEFAULT_SPEED;
        steering = DEFAULT_STEERING;
        hull = DEFAULT_HULL;
        firerate = DEFAULT_FIRERATE;
        firerange = DEFAULT_FIRERANGE;
        inventorySize = DEFAULT_INVENTORY_SIZE;
    }

    public float getSpeed() {
        return speed;
    }

    public float getSteering() {
        return steering;
    }

    public int getFireRate() {
        return firerate;
    }

    public float getFireRange() {
        return firerange;
    }


    /**
     *  DEVELOPER TOOLS
     *  The below code is used in the developer tools
     */
    public void incSpeed() {
        speed++;
    }

    public void decSpeed() {
        speed--;
    }
}
