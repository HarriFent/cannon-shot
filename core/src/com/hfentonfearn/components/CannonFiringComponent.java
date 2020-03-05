package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class CannonFiringComponent implements Component, Poolable {

    public int timer = 0;
    public boolean firing = false;
    public int firerate = 0;
    public float range = 0f;
    public Vector2 direction = null;

    private CannonFiringComponent() {}

    public void update(int rate, float range) {
        this.range = range;
        firerate = rate;
    }

    @Override
    public void reset() {
        timer = 0;
        firing = false;
        firerate = 0;
        range = 0f;
        direction = null;
    }
}
