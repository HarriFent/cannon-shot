package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.hfentonfearn.components.PlayerComponent;

public class PlayerInputSystem extends IteratingSystem {

    public PlayerInputSystem() {
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity player, float deltaTime) {
        /*VelocityComponent velocity = Components.velocity.get(player);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.driveVelocity = ACCELERATION_DRIVE;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.driveVelocity = -ACCELERATION_DRIVE /2;
        } else {
            velocity.driveVelocity = 0f;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.turnVelocity = ACCELERATION_TURN;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.turnVelocity = -ACCELERATION_TURN;
        } else {
            velocity.turnVelocity = 0;
        }*/
    }
}
