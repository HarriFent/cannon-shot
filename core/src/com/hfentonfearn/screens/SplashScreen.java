package com.hfentonfearn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hfentonfearn.CannonShot;
import com.hfentonfearn.helpers.AssetLoader;

public class SplashScreen implements Screen {

    private SpriteBatch batcher;
    private Sprite sprite;
    private CannonShot game;
    private long timer;

    public SplashScreen(CannonShot cannonShot) {
        this.game = cannonShot;
    }

    @Override
    public void show() {
        System.out.println("SplashScreen");
        timer = 255;
        sprite = new Sprite(AssetLoader.splash);
        sprite.setColor(1, 1, 1, 0);

        float x, y;
        x = (Gdx.graphics.getWidth() - sprite.getWidth()) / 2;
        y = (Gdx.graphics.getHeight() - sprite.getHeight()) / 2;
        sprite.setPosition(x,y);
        batcher = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        if (updateTimer())
            game.setScreen(new MainMenuScreen(game));

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        sprite.draw(batcher);
        batcher.end();
    }

    private boolean updateTimer() {
        sprite.setAlpha(timer);
        timer -= 2;
         return !(timer > 0);
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
        batcher.dispose();
        game.dispose();
    }
}
