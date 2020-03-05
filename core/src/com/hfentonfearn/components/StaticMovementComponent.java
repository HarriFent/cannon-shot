package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

public class StaticMovementComponent implements Component, Poolable {
    public Vector2 movement;

    private StaticMovementComponent() {}

    public StaticMovementComponent init (Vector2 movement) {
        this.movement = movement;
        return this;
    }

    @Override
    public void reset() {
        movement = null;
    }
}
