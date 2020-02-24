package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.VelocityComponent;
import com.hfentonfearn.ecs.Components;

import static com.hfentonfearn.utils.Constants.*;

public class PlayerMovementSystem extends IteratingSystem {

    private PhysicsComponent physics;
    private Vector2 currentVector;
    private Vector2 impulseVector = new Vector2();

    public PlayerMovementSystem() {
        super(Family.all(PlayerComponent.class,VelocityComponent.class,PhysicsComponent.class).get());
    }


    @Override
    protected void processEntity(Entity player, float deltaTime) {
        VelocityComponent velocity = Components.VELOCITY.get(player);
        physics = Components.PHYSICS.get(player);
        Body body = physics.getBody();

        if (velocity.angularVelocity != 0f) {
            body.applyTorque(velocity.angularVelocity, true);
            if (Math.abs(body.getAngularVelocity()) > VELOCITY_MAXTURNVEL) {
                body.setAngularVelocity(velocity.angularVelocity > 0 ? VELOCITY_MAXTURNVEL : -VELOCITY_MAXTURNVEL);
            }
        } else {
            if (Math.abs(body.getAngularVelocity()) > 0.1f) {
                body.applyTorque(body.getAngularVelocity() < 0 ? VELOCITY_DECELERATION : -VELOCITY_DECELERATION, true);
            } else {
                body.setAngularVelocity(0);
            }
        }

        currentVector = body.getLinearVelocity();
        if(velocity.linearVelocity != 0f) {
            // accelerate
            impulseVector.set(0f, -velocity.linearVelocity * deltaTime).rotate(body.getAngle());
            body.applyForceToCenter(impulseVector, true);
            currentVector = body.getLinearVelocity();

            if(currentVector.len() >= VELOCITY_MAXDRIVEVEL) {
                currentVector.nor().scl(VELOCITY_MAXDRIVEVEL);
                body.setLinearVelocity(currentVector);
            }
        } else {
            // decelerate
            impulseVector.set(currentVector).nor().scl(-1f * VELOCITY_DECELERATION);
            if(currentVector.len() / body.getMass() > 0.1f) {
                body.applyForceToCenter(impulseVector,true);
            } else {
                body.setLinearVelocity(0f, 0f);
            }
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
}
