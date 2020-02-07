package com.hfentonfearn.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Screen;
import com.hfentonfearn.CannonShot;
import com.hfentonfearn.entities.PlayerBoat;
import com.hfentonfearn.entitysystems.MovementSystem;

public class GameScreen implements Screen {
    private final CannonShot game;

    private Engine engine;
    private PlayerBoat playerBoat;

    public GameScreen(CannonShot game) {
        this.game = game;
    }

    @Override
    public void show() {
        engine = new Engine();

        //Add Engine Systems
        engine.addSystem(new MovementSystem());

        //Add Entities
        playerBoat = new PlayerBoat();
        engine.addEntity(playerBoat);
    }

    @Override
    public void render(float delta) {

        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {

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
        for (Entity e : engine.getEntities())
            e.removeAll();
        engine.removeAllEntities();
    }
}
