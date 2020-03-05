package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

import static com.hfentonfearn.utils.Constants.*;

public class ShipStatisticComponent implements Component , Poolable {

    private float speed;
    private float steering;
    private float hull;
    private int firerate;
    private float firerange;
    private int inventorySize;

    public boolean changed;

    private ShipStatisticComponent() {}

    public ShipStatisticComponent init () {
        return init(DEFAULT_SPEED, DEFAULT_STEERING, DEFAULT_HULL, DEFAULT_FIRERATE, DEFAULT_FIRERANGE, DEFAULT_INVENTORY_SIZE);
    }

    public ShipStatisticComponent init (float speed, float steering, float hull, int firerate, float firerange, int inventorySize) {
        this.speed = speed;
        this.steering = steering;
        this.hull = hull;
        this.firerate = firerate;
        this.firerange = firerange;
        this.inventorySize = inventorySize;

        changed = true;
        return this;
    }

    @Override
    public void reset() {
        speed = 0f;
        steering = 0f;
        hull = 0f;
        firerate = 0;
        firerange = 0f;
        inventorySize = 0;

        changed = true;
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

    public float getHull() {
        return hull;
    }

    public int getInventorySize() {
        return inventorySize;
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