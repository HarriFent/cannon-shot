package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.helpers.MappersHandler;

import static com.hfentonfearn.helpers.Constants.*;

public class PlayerMovementSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private PhysicsComponent physics;
    private static final float DRIFT_OFFSET = 1.0f;

    public PlayerMovementSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, PhysicsComponent.class, PlayerComponent.class).get());
    }

    public void update(float deltaTime) {
        /*TransformComponent transform = MappersHandler.transform.get(player);

        transform.setPosition(physics.body.getPosition().x * PPM, physics.body.getPosition().y * PPM);
        transform.angle = physics.body.getAngle() * PPM;*/
        for (Entity player : entities) {
            player = entities.get(0);
            physics = MappersHandler.physics.get(player);
            if (physics.mDrift < 1) {
                physics.mForwardSpeed = getForwardVelocity();
                physics.mLateralSpeed = getLateralVelocity();
                if (physics.mLateralSpeed.len() < DRIFT_OFFSET) {
                    killDrift();
                } else {
                    handleDrift();
                }
            }
        }
    }

    private void handleDrift() {
        final Vector2 forwardSpeed = getForwardVelocity();
        final Vector2 lateralSpeed = getLateralVelocity();
        physics.body.setLinearVelocity(forwardSpeed.x + lateralSpeed.x * physics.mDrift, forwardSpeed.y + lateralSpeed.y * physics.mDrift);
    }

    private Vector2 getForwardVelocity() {
        final Vector2 currentNormal = physics.body.getWorldVector(new Vector2(0, 1));
        final float dotProduct = currentNormal.dot(physics.body.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    public void killDrift() {
        physics.body.setLinearVelocity(physics.mForwardSpeed);
    }

    private Vector2 getLateralVelocity() {
        final Vector2 currentNormal = physics.body.getWorldVector(new Vector2(1, 0));
        final float dotProduct = currentNormal.dot(physics.body.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    private Vector2 multiply(float a, Vector2 v) {
        return new Vector2(a * v.x, a * v.y);
    }
}
