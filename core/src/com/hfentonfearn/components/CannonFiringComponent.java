package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class CannonFiringComponent implements Component {

    public int timer = 0;
    public boolean firing = false;
    public int firerate;
    public float range;

    public void update(int rate, float range) {
        this.range = range;
        firerate = rate;
    }
}
