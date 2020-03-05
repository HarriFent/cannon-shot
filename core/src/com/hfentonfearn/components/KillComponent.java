package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class KillComponent implements Component, Poolable {

    public boolean kill = false;
    public boolean fromAnimation = false;
    public boolean timed = false;
    public boolean fade = false;

    public int timer = 0;
    public int starttime = 0;

    private KillComponent() {}

    public KillComponent init (boolean fromAnimation) {
        this.fromAnimation = fromAnimation;
        return this;
    }

    public KillComponent init (int duration) {
        timed = true;
        timer = starttime = duration;
        return this;
    }

    @Override
    public void reset() {
        kill = false;
        fromAnimation = false;
        timed = false;
        fade = false;

        timer = 0;
        starttime = 0;
    }
}
