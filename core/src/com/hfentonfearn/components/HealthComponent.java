package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {

    public float max;
    public final float min = 0;
    public float value;

    public HealthComponent(int health) {
        max = health;
        value = health;
    }

    public float percentage() {
        return value/max;
    }

    public void damage(float amount) {
        value -= amount;
        if (value < 0) value = 0;
    }
}
