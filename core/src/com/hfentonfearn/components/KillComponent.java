package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class KillComponent implements Component, Poolable {

    public boolean kill = false;
    public boolean afterAnimation = false;
    public boolean timed = false;
    public boolean fade = false;
    public boolean exploding = false;

    public int timer = 0;
    public int starttime = 0;
    public float explodingRadius;

    private KillComponent() {}

    @Override
    public void reset() {
        kill = false;
        afterAnimation = false;
        timed = false;
        fade = false;

        timer = 0;
        starttime = 0;
    }

    public float getProgress() {
        return (float) timer / (float) starttime;
    }
}
