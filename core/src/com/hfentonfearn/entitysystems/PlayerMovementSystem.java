package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.AccelerationComponent;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.utils.Constants.VELOCITY_DRIFT;

public class PlayerMovementSystem extends IteratingSystem {

    private PhysicsComponent physics;

    public PlayerMovementSystem() {
        super(Family.all(PlayerComponent.class, AccelerationComponent.class,PhysicsComponent.class).get());
    }


    @Override
    protected void processEntity(Entity player, float deltaTime) {
        AccelerationComponent acceleration = Components.ACCELERATION.get(player);
        physics = Components.PHYSICS.get(player);
        Body body = physics.getBody();

        if (acceleration.angular != 0f) {
            body.applyTorque(acceleration.angular, true);
        }

        Vector2 impulseVector = new Vector2();
        if(acceleration.linear != 0f) {
            // accelerate
            impulseVector.set(0f, -acceleration.linear * deltaTime).rotate( MathUtils.radiansToDegrees * body.getAngle());
            EntityFactory.createParticle(physics.getPosition().cpy().sub(impulseVector.cpy().scl(100)), ParticleSystem.ParticleType.WATER, (float) Math.toDegrees(body.getAngle())+90);
            body.applyForceToCenter(impulseVector, true);
        }
        handleDrift();
    }

    private void handleDrift() {
        final Vector2 forwardSpeed = getForwardVelocity();
        final Vector2 lateralSpeed = getLateralVelocity();
        physics.getBody().setLinearVelocity(forwardSpeed.x + lateralSpeed.x * VELOCITY_DRIFT, forwardSpeed.y + lateralSpeed.y * VELOCITY_DRIFT);
    }

    private Vector2 getForwardVelocity() {
        final Vector2 currentNormal = physics.getBody().getWorldVector(new Vector2(0, 1));
        final float dotProduct = currentNormal.dot(physics.getBody().getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    private Vector2 getLateralVelocity() {
        final Vector2 currentNormal = physics.getBody().getWorldVector(new Vector2(1, 0));
        final float dotProduct = currentNormal.dot(physics.getBody().getLinearVelocity());
        return multiply(dotProduct, currentNormal);
    }

    private Vector2 multiply(float a, Vector2 v) {
        return new Vector2(a * v.x, a * v.y);
    }

    @Override
    public boolean checkProcessing () {
        return !GameManager.isPaused();
    }
}
