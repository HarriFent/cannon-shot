package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {

    public float max;
    public float value;

    public HealthComponent(float health) {
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
