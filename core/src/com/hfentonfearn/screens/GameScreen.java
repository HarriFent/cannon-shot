package com.hfentonfearn.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.ecs.EntityManager;
import com.hfentonfearn.entitysystems.CameraSystem;
import com.hfentonfearn.entitysystems.GUISystem;
import com.hfentonfearn.entitysystems.InputSystem;
import com.hfentonfearn.inputs.DeveloperInputProcessor;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.Components;
import com.hfentonfearn.utils.WorldBuilder;

public class GameScreen extends AbstractScreen {

    private EntityManager engine;
    private InputMultiplexer multiplexer;

    public GameScreen() {
    }

    @Override
    public void show() {
        engine = GameManager.initEngine();
        createWorld(AssetLoader.map.width, AssetLoader.map.height);

        multiplexer = engine.getSystem(InputSystem.class).getMultiplexer();
        multiplexer.addProcessor(engine.getSystem(GUISystem.class).getStage());
        multiplexer.addProcessor(new DeveloperInputProcessor());
    }

    private void createWorld(int width, int height) {
        WorldBuilder worldBuilder = new WorldBuilder(width, height);
        worldBuilder.createWorld();
        Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
        Vector2 playerpos = Components.PHYSICS.get(player).getPosition();
        engine.getSystem(CameraSystem.class).setTargetEntity(player);
        engine.getSystem(CameraSystem.class).setWorldBounds(width, height);
        engine.getSystem(CameraSystem.class).goTo(playerpos.x, playerpos.y);
        engine.getSystem(CameraSystem.class).zoom(0.5f);
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
