package com.hfentonfearn.screens;

import com.badlogic.gdx.InputMultiplexer;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.ecs.EntityManager;
import com.hfentonfearn.entitysystems.CameraSystem;
import com.hfentonfearn.entitysystems.GUISystem;
import com.hfentonfearn.entitysystems.InputSystem;

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
        engine.getSystem(CameraSystem.class).getCamera().position.set(width * 0.1f, height * 0.1f, 0);
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
