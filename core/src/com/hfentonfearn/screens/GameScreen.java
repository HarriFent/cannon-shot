package com.hfentonfearn.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.physics.box2d.Body;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.ecs.Components;
import com.hfentonfearn.ecs.EntityManager;
import com.hfentonfearn.entitysystems.CameraSystem;
import com.hfentonfearn.entitysystems.GUISystem;
import com.hfentonfearn.entitysystems.InputSystem;
import com.hfentonfearn.utils.WorldBuilder;

public class GameScreen extends AbstractScreen {

    private EntityManager engine;
    private InputMultiplexer multiplexer;

    public GameScreen() {
    }

    @Override
    public void show() {
        engine = GameManager.initEngine();
        createWorld(6400, 6400);

        multiplexer = engine.getSystem(InputSystem.class).getMultiplexer();
        multiplexer.addProcessor(engine.getSystem(GUISystem.class).getStage());
    }

    private void createWorld(int width, int height) {
        WorldBuilder worldBuilder = new WorldBuilder(width, height);
        worldBuilder.createWorld();
        Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
        Body body = Components.PHYSICS.get(player).getBody();
        engine.getSystem(CameraSystem.class).getCamera().position.set(body.getPosition().x, body.getPosition().y, 0);
        engine.getSystem(CameraSystem.class).setWorldBounds(width, height);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        engine.getSystem(GUISystem.class).resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
