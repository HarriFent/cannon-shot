package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.VelocityComponent;
import com.hfentonfearn.helpers.MappersHandler;

public class PlayerInputSystem extends EntitySystem {

    private ImmutableArray<Entity> players;

    public PlayerInputSystem() {}

    public void addedToEngine(Engine engine) {
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity player : players) {
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
}
