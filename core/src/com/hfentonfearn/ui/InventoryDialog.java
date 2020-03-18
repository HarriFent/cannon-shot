package com.hfentonfearn.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hfentonfearn.GameManager;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class InventoryDialog extends Window {

    public InventoryDialog(Skin skin) {
        super("", skin, "inventory");
        setMovable(false);
        setModal(true);

        TextButton closeButton = new TextButton("Close", skin);
        closeButton.pad(10);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.resume();
                remove();
            }
        });
    }

    public void show (Stage stage) {
        show(stage, sequence(Actions.alpha(0), Actions.fadeIn(3f, Interpolation.fade)));
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));
    }

    public void show (Stage stage, Action action) {
        clearActions();
        stage.addActor(this);
        pack();
        stage.cancelTouchFocus();
        stage.setKeyboardFocus(this);
        stage.setScrollFocus(this);
        if (action != null) addAction(action);
    }
}
