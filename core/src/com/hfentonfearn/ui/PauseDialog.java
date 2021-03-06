package com.hfentonfearn.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.screens.MainMenuScreen;

public class PauseDialog extends Dialog {

    public PauseDialog(Skin skin) {
        super("Pause", skin);
        setPosition((Gdx.graphics.getWidth() * 0.5f) - (getWidth() * 0.5f), (Gdx.graphics.getHeight() * 0.5f)
                - (getHeight() * 0.5f));
        setMovable(false);
        setModal(true);

        TextButton mainMenuButton = new TextButton("Main Menu", skin);
        mainMenuButton.pad(10);
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.disposeEngine();
                GameManager.setScreen(new MainMenuScreen());
            }
        });

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.pad(10);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.resume();
                hide();
            }
        });

        TextButton exitButton = new TextButton("Exit", skin);
        exitButton.pad(10);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.exit();
            }
        });

        getButtonTable().add(mainMenuButton).pad(5).prefWidth(200);
        getButtonTable().row();
        getButtonTable().add(closeButton).prefWidth(200);
        getButtonTable().row();
        getButtonTable().add(exitButton).pad(5).prefWidth(200);

    }
}
