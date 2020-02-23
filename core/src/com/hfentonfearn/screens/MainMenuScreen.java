package com.hfentonfearn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.utils.AssetLoader;
import com.hfentonfearn.utils.Constants;
import com.hfentonfearn.ui.OptionsDialog;

public class MainMenuScreen extends AbstractScreen {

    private Stage stage;
    private Table table;

    @Override
    public void show() {

        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        table.defaults().width(Gdx.graphics.getWidth() / 4).pad(20);

        addBackground();
        add("Play Game", new GameScreen());
        add("Credits", new CreditsScreen());
        addOption();
        addExit();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    private void addOption() {
        NinePatchDrawable draw = new NinePatchDrawable(AssetLoader.hotkey.button);

        TextButton.TextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = draw;
        style.over = draw.tint(Color.GRAY);
        style.down = draw.tint(Color.DARK_GRAY);
        style.checked = draw;
        style.font = AssetLoader.fonts.font;

        TextButton optionsButton = new TextButton("Options", style);
        optionsButton.addListener(new ChangeListener(){
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                OptionsDialog dialog = new OptionsDialog(AssetLoader.skin);
                dialog.show(stage);
            }
        });

        table.add(optionsButton);
        table.row();
    }

    private void addExit() {
        NinePatchDrawable draw = new NinePatchDrawable(AssetLoader.hotkey.button);

        TextButton.TextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.up = draw;
        style.over = draw.tint(Color.GRAY);
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
        style.over = draw.tint(Color.GRAY);
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

    private void addBackground() {
        Texture texture = AssetLoader.ui.mainMenu;
        Image itemImage = new Image();
        itemImage.setPosition(10, 10);
        itemImage.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
        itemImage.setSize(texture.getWidth(), texture.getHeight());
        stage.addActor(itemImage);
    }

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
