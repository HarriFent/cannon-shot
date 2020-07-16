package com.hfentonfearn.screens;

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

import static com.hfentonfearn.GameManager.GameConfig.BUILD;
import static com.hfentonfearn.GameManager.GameConfig.build;
import static com.hfentonfearn.utils.Constants.DEBUGMODE;

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
        if (build == BUILD.DEV) {
            //DEBUGMODE = true;
            multiplexer.addProcessor(new DeveloperInputProcessor());
        }
    }

    private void createWorld(int width, int height) {
        WorldBuilder worldBuilder = new WorldBuilder(width, height);
        worldBuilder.createWorld();
        Vector2 playerpos = Components.PHYSICS.get(PlayerComponent.player).getPosition();
        engine.getSystem(CameraSystem.class).setTargetEntity(PlayerComponent.player);
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
