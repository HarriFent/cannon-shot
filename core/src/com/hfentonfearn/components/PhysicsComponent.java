package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PhysicsComponent implements Component {

    public Vector2 mForwardSpeed;
    public Vector2 mLateralSpeed;

    public Body body;
    public float mDrift = 1;
}
