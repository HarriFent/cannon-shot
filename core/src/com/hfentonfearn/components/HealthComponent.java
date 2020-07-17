package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

import java.util.Timer;
import java.util.TimerTask;

public class HealthComponent implements Component, Poolable {

    public float max;
    public float value;
    private float displayValue;
    private Timer t;

    private HealthComponent() {}

    public HealthComponent init (float health) {
        value = health;
        displayValue = health;
        return this;
    }

    public float percentage() {
        return value/max;
    }

    public void damage(float amount) {
        displayValue = value;
        value -= amount;
        if (value < 0) value = 0;
    }

    @Override
    public void reset() {
        max = 0;
        value = 0;
    }

    public float getDisplayValue() {
        if (Math.abs(displayValue - value) > 1 && t == null) {
            t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    displayValue += Math.signum(value - displayValue)/2;
                }
            }, 0, 500);
        }
        System.out.println("Display: " + displayValue + ", Value: " + value + ", abs(dis - val): " + Math.abs(displayValue - value));
        if (Math.abs(displayValue - value) < 0.5 && t != null) {
            displayValue = value;
            t.cancel();
            t = null;
        }
        return displayValue;
    }

    public void fullHeal() {
        value = max;
    }

    public void heal(int amount) {
        displayValue = value;
        value += amount;
    }
}
