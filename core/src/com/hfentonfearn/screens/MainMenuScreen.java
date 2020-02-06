package com.hfentonfearn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hfentonfearn.CannonShot;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private Table table;
    private CannonShot game;

    private SpriteBatch batcher;

    MainMenuScreen(CannonShot game) {
        this.game = game;
    }

    @Override
    public void show() {
        System.out.println("MainMenu");
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(true);

        Skin mySkin = new Skin(Gdx.files.internal("skin/level-plane-ui.json"));

        table.add().expand().colspan(2);
        table.row();

        TextButton txtPlayButton = new TextButton("Play",mySkin);
        txtPlayButton.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }
        });
        table.add(txtPlayButton).pad(20);

        TextButton txtOptionButton = new TextButton("Play",mySkin);
        txtOptionButton.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionScreen(game));
            }
        });
        table.add(txtOptionButton).pad(20);

        table.add().expandX();

        batcher = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        batcher.begin();
        stage.draw();
        batcher.end();
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
        stage.dispose();
    }
}
