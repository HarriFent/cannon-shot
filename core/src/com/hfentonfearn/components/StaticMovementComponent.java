package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class StaticMovementComponent implements Component {
    public Vector2 movement;

    public StaticMovementComponent(Vector2 movement) {
        this.movement = movement;
    }
}
