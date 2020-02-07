package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hfentonfearn.components.AccelerationComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.components.VelocityComponent;

public class PlayerControllerSystem extends EntitySystem {

    private ImmutableArray<Entity> players;

    private ComponentMapper<AccelerationComponent> am = ComponentMapper.getFor(AccelerationComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<TransformComponent> pm = ComponentMapper.getFor(TransformComponent.class);

    public PlayerControllerSystem() {}

    public void addedToEngine(Engine engine) {
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
    }

    public void update(float deltaTime) {
        float speed = deltaTime;
        for (Entity player : players) {
            AccelerationComponent acceleration = am.get(player);
            TransformComponent transform = pm.get(player);
            acceleration.clear();
            boolean moving = false;
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                acceleration.setTangentAcc(speed);
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                acceleration.setTangentAcc(-speed);
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                acceleration.setAngleAcc(speed);
                moving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                acceleration.setAngleAcc(-speed);
                moving = true;
            }

        }
    }
}
