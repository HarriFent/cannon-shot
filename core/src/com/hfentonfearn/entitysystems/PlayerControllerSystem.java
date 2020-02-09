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
        float speed = deltaTime * 10;
        for (Entity player : players) {
            AccelerationComponent acceleration = MappersHandler.acceleration.get(player);
            acceleration.clear();
            float newTangAcc = 0.0f;
            float newAngleAcc = 0.0f;

            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                newTangAcc = speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                newTangAcc = -speed/3;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                newAngleAcc = speed;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                newAngleAcc = -speed;
            }
            VelocityComponent velocity = MappersHandler.velocity.get(player);

            if(newTangAcc <= 0 && velocity.getTangentVel() > 0) {
                newTangAcc -= speed;
            }
            if (newTangAcc >= 0 && velocity.getTangentVel() < 0) {
                newTangAcc += speed;
            }
            if(newAngleAcc <= 0 && velocity.getAngleVel() > 0) {
                newAngleAcc -= speed;
            }
            if (newAngleAcc >= 0 && velocity.getAngleVel() < 0) {
                newAngleAcc += speed;
            }

            acceleration.setTangentAcc(newTangAcc);
            acceleration.setAngleAcc(newAngleAcc);
        }
    }
}
