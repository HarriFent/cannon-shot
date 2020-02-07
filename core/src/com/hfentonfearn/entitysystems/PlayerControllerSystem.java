package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.PositionComponent;
import com.hfentonfearn.components.VelocityComponent;

import java.awt.event.KeyEvent;

public class PlayerControllerSystem extends EntitySystem {

    private ImmutableArray<Entity> players;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);

    public PlayerControllerSystem() {}

    public void addedToEngine(Engine engine) {
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity player : players) {
            VelocityComponent velocity = vm.get(player);
            if (Gdx.input.isKeyPressed(KeyEvent.VK_W)) {
                velocity.y = deltaTime;
            } else if (Gdx.input.isKeyPressed(KeyEvent.VK_S)) {
                velocity.y = -deltaTime;
            } else if (Gdx.input.isKeyPressed(KeyEvent.VK_A)) {
                velocity.x = -deltaTime;
            } else if (Gdx.input.isKeyPressed(KeyEvent.VK_D)) {
                velocity.x = deltaTime;
            }
        }
    }
}
