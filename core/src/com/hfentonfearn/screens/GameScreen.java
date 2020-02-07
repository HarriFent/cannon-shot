package com.hfentonfearn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hfentonfearn.CannonShot;
import com.hfentonfearn.gameworld.GameWorld;

public class GameScreen implements Screen {

    private final CannonShot game;
    private GameWorld gameWorld;
    private SpriteBatch batch;


    public GameScreen(CannonShot game) {
        this.game = game;
        System.out.println("Game Loaded");
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        gameWorld = new GameWorld(batch);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameWorld.update(delta);
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
        gameWorld.dispose();
    }
}
