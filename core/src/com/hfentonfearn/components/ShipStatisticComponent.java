package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

import static com.hfentonfearn.utils.Constants.*;

public class ShipStatisticComponent implements Component {

    public float speed;
    public float steering;
    public float hull;
    public int firerate;
    public float firerange;
    public float inventorySize;

    public ShipStatisticComponent() {
        speed = DEFAULT_SPEED;
        steering = DEFAULT_STEERING;
        hull = DEFAULT_HULL;
        firerate = DEFAULT_FIRERATE;
        firerange = DEFAULT_FIRERANGE;
        inventorySize = DEFAULT_INVENTORY_SIZE;
    }

}
