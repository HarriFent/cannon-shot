package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class HealthComponent implements Component {

    public final int max;
    public final int min = 0;
    public int value;

    public HealthComponent(int health) {
        max = value = health;
    }
}
