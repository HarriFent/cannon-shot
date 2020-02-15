package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.components.PhysicsComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.components.VelocityComponent;
import com.hfentonfearn.helpers.MappersHandler;

import static com.hfentonfearn.helpers.Constants.*;

public class PlayerMovementSystem extends IteratingSystem {

    private PhysicsComponent physics;
    private Vector2 currentVector;
    private Vector2 impulseVector = new Vector2();

    public PlayerMovementSystem() {
        super(Family.all(PlayerComponent.class).get());
    }


    @Override
    protected void processEntity(Entity player, float deltaTime) {
        TransformComponent transform = MappersHandler.transform.get(player);
        VelocityComponent velocity = MappersHandler.velocity.get(player);
        physics = MappersHandler.physics.get(player);
        currentVector = physics.body.getLinearVelocity();


        if (velocity.turnVelocity != 0f) {
            //physics.body.setAngularVelocity(velocity.turnVelocity);
            physics.body.applyTorque(velocity.turnVelocity, true);
        } else {
            physics.body.setAngularVelocity(0);
        }

        if(velocity.driveVelocity != 0f) {
            // accelerate
            impulseVector.set(0f, -velocity.driveVelocity * deltaTime).rotate(transform.rotation);

            // impulseVector has to be applied directly to allow direction changes at max velocity
            //physics.body.applyLinearImpulse(impulseVector, currentPos, true);
            physics.body.applyForceToCenter(impulseVector, true);
            currentVector = physics.body.getLinearVelocity();

            if(currentVector.len() >= VELOCITY_MAXDRIVEVEL) {
                // set velocity to maxVel
                currentVector.nor().scl(VELOCITY_MAXDRIVEVEL);
                physics.body.setLinearVelocity(currentVector);
            }
        } else {
            // decelerate
            impulseVector.set(currentVector).nor().scl(-1f * VELOCITY_DECELERATION);

            if(currentVector.len() / physics.body.getMass() > 0.1f) {
                // only apply impulse does not stop and accelerate the body in the opposite direction
                //physics.body.applyLinearImpulse(impulseVector, currentPos, true);
                physics.body.applyForceToCenter(impulseVector,true);
            } else {
                // if impulse would accelerate in opposite direction set velocity to 0 instead
                physics.body.setLinearVelocity(0f, 0f);
            }
        }

        handleDrift();

        transform.position = new Vector2(physics.body.getPosition().x * PPM, physics.body.getPosition().y * PPM);
        transform.rotation = (float) Math.toDegrees(physics.body.getAngle());
    }



    private void handleDrift() {
        final Vector2 forwardSpeed = getForwardVelocity();
        final Vector2 lateralSpeed = getLateralVelocity();
        physics.body.setLinearVelocity(forwardSpeed.x + lateralSpeed.x * VELOCITY_DRIFT, forwardSpeed.y + lateralSpeed.y * VELOCITY_DRIFT);
    }

    private Vector2 getForwardVelocity() {
        final Vector2 currentNormal = physics.body.getWorldVector(new Vector2(0, 1));
        final float dotProduct = currentNormal.dot(physics.body.getLinearVelocity());
        return multiply(dotProduct, currentNormal);
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
