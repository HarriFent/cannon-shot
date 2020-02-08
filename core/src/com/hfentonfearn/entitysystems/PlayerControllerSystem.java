package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hfentonfearn.components.AccelerationComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.components.VelocityComponent;
import com.hfentonfearn.helpers.MappersHandler;

public class PlayerControllerSystem extends EntitySystem {

    private ImmutableArray<Entity> players;

    public PlayerControllerSystem() {}

    public void addedToEngine(Engine engine) {
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    public void update(float deltaTime) {
        float speed = deltaTime;
        for (Entity player : players) {
            AccelerationComponent acceleration = MappersHandler.acceleration.get(player);
            acceleration.clear();
            boolean moveTangent = false;
            boolean moveAngle = false;
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                acceleration.setTangentAcc(speed);
                moveTangent = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                acceleration.setTangentAcc(-speed);
                moveTangent = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                acceleration.setAngleAcc(speed);
                moveAngle = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                acceleration.setAngleAcc(-speed);
                moveAngle = true;
            }
            VelocityComponent velocity = MappersHandler.velocity.get(player);
            if (!moveTangent) {
                float newAcc = velocity.getTangentVel() / Math.abs(velocity.getTangentVel());
                if (Math.abs(velocity.getTangentVel()) > 0)
                    acceleration.setTangentAcc(-newAcc * speed);
            }
            if (!moveAngle) {
                float newAcc = velocity.getAngleVel() / Math.abs(velocity.getAngleVel());
                if (Math.abs(velocity.getAngleVel()) > 0)
                    acceleration.setAngleAcc(-newAcc * speed);
            }
        }
    }
}