package com.hfentonfearn.screens;

import com.badlogic.gdx.Screen;
import com.hfentonfearn.CannonShot;
import com.hfentonfearn.gameworld.GameWorld;

public class GameScreen implements Screen {

    private final CannonShot game;
    private GameWorld gameWorld;


    public GameScreen(CannonShot game) {
        this.game = game;
    }

    @Override
    public void show() {
        gameWorld = new GameWorld();
    }

    @Override
    public void render(float delta) {
        /*Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);*/
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
