package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.VelocityComponent;
import com.hfentonfearn.helpers.MappersHandler;

public class PlayerInputSystem extends IteratingSystem {

    public PlayerInputSystem() {
        super(Family.all(PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity player, float deltaTime) {
        VelocityComponent velocity = MappersHandler.velocity.get(player);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.driveVelocity = 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.driveVelocity = -2;
        } else {
            velocity.driveVelocity = 0;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.turnVelocity = 2;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.turnVelocity = -2;
        } else {
            velocity.turnVelocity = 0;
        }
    }
}
