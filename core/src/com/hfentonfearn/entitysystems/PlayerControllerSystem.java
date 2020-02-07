package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hfentonfearn.components.AccelerationComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.VelocityComponent;

public class PlayerControllerSystem extends EntitySystem {

    private ImmutableArray<Entity> players;

    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<AccelerationComponent> am = ComponentMapper.getFor(AccelerationComponent.class);

    public PlayerControllerSystem() {}

    public void addedToEngine(Engine engine) {
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity player : players) {
            VelocityComponent velocity = vm.get(player);
            AccelerationComponent acceleration = am.get(player);
            boolean moving = false;
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                acceleration.y = 10;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                acceleration.y = -10;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                acceleration.x = -10;
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                acceleration.x = 10;
                moving = true;
            }

            if (!moving) {
                if (velocity.x != 0) {
                    velocity.x -= acceleration.x;
                } else {
                    acceleration.x = 0;
                }
                if (velocity.y != 0) {
                    velocity.y -= acceleration.y;
                } else {
                    acceleration.y = 0;
                }
            } else {
                if (Math.abs(velocity.x) < 100) velocity.x += acceleration.x;
                if (Math.abs(velocity.y) < 100) velocity.y += acceleration.y;
            }
        }
    }
}
