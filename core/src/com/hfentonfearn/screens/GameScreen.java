package com.hfentonfearn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.hfentonfearn.CannonShot;
import com.hfentonfearn.gameworld.GameWorld;
import com.hfentonfearn.helpers.FrameRate;

public class GameScreen implements Screen {

    private final CannonShot game;
    private GameWorld gameWorld;
    private FrameRate frameRate;


    public GameScreen(CannonShot game) {
        this.game = game;
        this.frameRate = new FrameRate();
    }

    @Override
    public void show() {
        gameWorld = new GameWorld();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameWorld.update(delta);
        System.out.println();
        frameRate.update();
        frameRate.render();
    }

    @Override
    public void resize(int width, int height) {
        frameRate.resize(width,height);
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
