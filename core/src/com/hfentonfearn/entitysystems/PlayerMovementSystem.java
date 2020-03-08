package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
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
            body.applyForceToCenter(impulseVector, true);
        }

        if (getForwardVelocity().len() > 0) {
            Vector2 particlePos = new Vector2(15,55).rotateRad(physics.getBody().getAngle()).add(physics.getPosition());
            Entity wake = EntityFactory.createParticle(particlePos, ParticleSystem.ParticleType.WATER, (float) Math.toDegrees(body.getAngle()+70));
            Vector2 particlePos2 = new Vector2(-15,55).rotateRad(physics.getBody().getAngle()).add(physics.getPosition());
            Entity wake2 = EntityFactory.createParticle(particlePos2, ParticleSystem.ParticleType.WATER, (float) Math.toDegrees(body.getAngle()+110));
            Components.PARTICLE.get(wake).setVelocity(getForwardVelocity().cpy().scl(100).len());
            Components.PARTICLE.get(wake2).setVelocity(getForwardVelocity().cpy().scl(100).len());
            float alpha = getForwardVelocity().len()/10 > 1 ? 1 : getForwardVelocity().len()/10;
            Components.PARTICLE.get(wake).setAlpha(alpha);
            Components.PARTICLE.get(wake2).setAlpha(alpha);
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
