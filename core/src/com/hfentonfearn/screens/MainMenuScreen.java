package com.hfentonfearn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hfentonfearn.CannonShot;
import com.hfentonfearn.helpers.AssetLoader;

public class MainMenuScreen implements Screen {

    private Stage stage;
    private Table table;
    private CannonShot game;

    private Sprite background;

    private SpriteBatch batcher;

    MainMenuScreen(CannonShot game) {
        this.game = game;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        CreateUITable(new Skin(Gdx.files.internal("skin/level-plane-ui.json")));

        background = new Sprite(AssetLoader.bgMainMenu);
        background.setOriginCenter();
        background.setScale(2);

        batcher = new SpriteBatch();
    }

    private void CreateUITable(Skin skin) {
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(false);
        table.add().expand().colspan(3);
        table.row();
        TextButton txtPlayButton = new TextButton("Play",skin, "big-1");
        txtPlayButton.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }
        });
        table.add(txtPlayButton).pad(25,50,50,25);
        TextButton txtOptionButton = new TextButton("Options",skin, "big-1");
        txtOptionButton.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionScreen(game));
            }
        });
        table.add(txtOptionButton).pad(25,25,50,25);
        table.add().expandX();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();

        batcher.begin();
        background.draw(batcher);
        batcher.end();

        stage.draw();
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
