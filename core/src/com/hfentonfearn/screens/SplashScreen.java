package com.hfentonfearn.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.helpers.AssetLoader;

public class SplashScreen extends AbstractScreen {

    Texture logo;
    float alpha = 0;

    Stage stage;
    Table table;

    @Override
    public void show() {

        logo = new Texture(Gdx.files.internal("flipflopSplash.png"));
        logo.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        stage = new Stage();
        table = new Table();
        table.setFillParent(true);

        Image flipflop = new Image(logo);
        flipflop.setOrigin(flipflop.getWidth() / 2, flipflop.getHeight() / 2);
        flipflop.addAction(new Fade());

        table.add(flipflop);
        stage.addActor(table);
        AssetLoader.load();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (alpha >= 1) {
            if (AssetLoader.getManager().update()) {
                AssetLoader.create();
                GameManager.setScreen(new MainMenuScreen());
            }
        }

        if (alpha >= 1)
            alpha = 1;
        else
            alpha += 0.25f * delta;

        stage.act();
        stage.draw();
    }

    private class Fade extends Action {
        @Override
        public boolean act(float delta) {
            getActor().setColor(1, 1, 1, alpha);
            return false;
        }
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
        logo.dispose();
    }
}
