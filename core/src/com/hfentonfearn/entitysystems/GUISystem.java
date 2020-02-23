package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;
import com.hfentonfearn.helpers.AssetLoader;

public class GUISystem extends EntitySystem implements Disposable {

    private Stage stage;
    private Skin skin;

    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color ALERT_COLOR = Color.RED;

    public GUISystem () {
        this.stage = new Stage();
        this.skin = AssetLoader.skin;

        initGUI();
    }

    @Override
    public void addedToEngine (Engine engine) {
        super.addedToEngine(engine);
    }

    private void initGUI () {
        //Initialise GUI
    }

    public void resize (int screenWidth, int screenHeight) {
        stage.getViewport().update(screenWidth, screenHeight);
    }

    @Override
    public void update (float deltaTime) {
        super.update(deltaTime);

        stage.act();
        stage.draw();
    }

    public Stage getStage () {
        return stage;
    }

    @Override
    public void dispose () {
        stage.dispose();
    }
}
