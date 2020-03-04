package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class CannonFiringComponent implements Component {

    public int timer = 0;
    public boolean firing = false;
    public int firerate;
    public float range;
    public Vector2 direction;

    public void update(int rate, float range) {
        this.range = range;
        firerate = rate;
    }
}
