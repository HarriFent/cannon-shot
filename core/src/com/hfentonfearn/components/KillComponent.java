package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class KillComponent implements Component {

    public boolean kill = false;
    public boolean fromAnimation = false;
    public boolean timed = false;
    public boolean fade = false;

    public int timer = 0;
    public int starttime = 0;

    public KillComponent() {}

    public KillComponent(boolean fromAnimation) {
        this.fromAnimation = fromAnimation;
    }

    public KillComponent(int duration) {
        timed = true;
        timer = starttime = duration;
    }
}
