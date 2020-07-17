package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class HealthComponent implements Component, Poolable {

    public float max;
    public float value;
    public float displayValue;

    private HealthComponent() {}

    public HealthComponent init (float health) {
        value = health;
        displayValue = health;
        max = health;
        return this;
    }

    public float percentage() {
        return max == 0 ? 0 : displayValue/max;
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

    public void fullHeal() {
        value = max;
    }

    public void heal(int amount) {
        displayValue = value;
        value += amount;
    }

    public void set(float amount) {
        value = amount;
        displayValue = amount;
    }
}
