package com.hfentonfearn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.Main;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.Constants;
import com.hfentonfearn.ui.OptionsDialog;

public class MainMenuScreen extends AbstractScreen {

    private Stage stage;
    private Table table;

    @Override
    public void show() {

        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        table.defaults().width(Gdx.graphics.getWidth() / 2).pad(20);

        addTitle();
        add("Play Game", new GameScreen());
        add("Credits", new CreditsScreen());
        addExit();

        final Skin skin = AssetLoader.skin;

        TextButton optionsButton = new TextButton("Options", skin);
        optionsButton.addListener(new ChangeListener(){
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                OptionsDialog dialog = new OptionsDialog(skin);
                dialog.show(stage);
            }
        });

        table.add(optionsButton);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void addTitle() {
        Label title = new Label(Constants.GAME_TITLE, AssetLoader.skin);
        title.setAlignment(Align.center);
        table.add(title);
        table.row();
    }

    private void addExit() {
        NinePatchDrawable draw = new NinePatchDrawable(AssetLoader.hotkey.button);

        TextButton.TextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = draw;
        style.down = draw.tint(Color.DARK_GRAY);
        style.checked = draw;
        style.font = AssetLoader.fonts.font;

        TextButton btn = new TextButton("Exit", style);
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Gdx.app.exit();
            }
        });
        table.add(btn);
        table.row();
    }

    public void add(String title, final AbstractScreen screen) {
        NinePatchDrawable draw = new NinePatchDrawable(AssetLoader.hotkey.button);

        TextButton.TextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = draw;
        style.down = draw.tint(Color.DARK_GRAY);
        style.checked = draw;
        style.font = AssetLoader.fonts.font;

        TextButton btn = new TextButton(title, style);
        btn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GameManager.setScreen(screen);
            }
        });
        table.add(btn);
        table.row();

    }

    /*private void CreateUITable(Skin skin) {
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        table.setDebug(false);
        table.add().expand().colspan(3);
        table.row();
        TextButton txtPlayButton = new TextButton("Play",skin,"big-1");
        txtPlayButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
            }
        });
        table.add(txtPlayButton).pad(25,50,50,25);
        TextButton txtOptionButton = new TextButton("Options",skin,"big-1");
        txtOptionButton.addListener(new InputListener() {
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new OptionScreen(game));
            }
        });
        table.add(txtOptionButton).pad(25,25,50,25);
        table.add().expandX();
    }*/

    @Override
    public void render(float delta) {
        super.render(delta);
        stage.draw();
        stage.act();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        stage.getCamera().update();
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
