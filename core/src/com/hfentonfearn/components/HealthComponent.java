package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class HealthComponent implements Component, Poolable {

    public float max;
    public float value;

    private HealthComponent() {}

    public HealthComponent init (float health) {
        value = health;
        return this;
    }

    public float percentage() {
        return value/max;
    }

    public void damage(float amount) {
        value -= amount;
        if (value < 0) value = 0;
    }

    @Override
    public void reset() {
        max = 0;
        value = 0;
    }
}
