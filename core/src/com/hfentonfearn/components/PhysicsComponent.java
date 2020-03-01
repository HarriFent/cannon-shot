package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool.Poolable;

import static com.hfentonfearn.utils.Constants.PPM;

public class PhysicsComponent implements Component, Poolable {

    private Body body;

    /** Can only be created by PooledEngine */
    private PhysicsComponent () {
        // private constructor
    }

    public PhysicsComponent init (Body body) {
        this.body = body;
        return this;
    }

    public Body getBody () {
        return body;
    }

    public Vector2 getPosition() {
        return body.getPosition().scl(PPM);
    }

    @Override
    public void reset () {
        body = null;
    }
}
