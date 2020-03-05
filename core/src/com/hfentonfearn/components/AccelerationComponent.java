package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AccelerationComponent implements Component, Poolable {
    public float angular;
    public float linear;

    private AccelerationComponent() {}

    public AccelerationComponent init () {
        angular = 0f;
        linear = 0f;
        return this;
    }

    @Override
    public void reset() {
        angular = 0f;
        linear = 0f;
    }
}
