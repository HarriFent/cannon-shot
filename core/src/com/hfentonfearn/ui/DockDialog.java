package com.hfentonfearn.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.ShipStatisticComponent;
import com.hfentonfearn.utils.Components;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class DockDialog extends Window {

    Table contentTable;
    private Skin skin;

    public DockDialog(Skin skin) {
        super("", skin, "inventory");
        setSkin(skin);
        this.skin = skin;
        initialize();
    }

    private void initialize() {
        add(contentTable = new Table(skin)).expand().fill();

        setMovable(false);
        setModal(true);

        TextButton closeButton = new TextButton("Close", skin, "inventory");
        closeButton.pad(10);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.resume();
                remove();
            }
        });

        ShipStatisticComponent stats = Components.STATS.get(PlayerComponent.player);
        createStatRow("Range:", stats.getFirerange());

        contentTable.add(closeButton);
    }

    private void createStatRow(String s, float f) {

        Label lbl = new Label(s, skin, "inventory");
        contentTable.row();
        contentTable.add(lbl);

        TextButton btn = new TextButton("+", skin);
        btn.
        contentTable.add();

    }

    public void show (Stage stage) {
        show(stage, sequence(Actions.alpha(0), Actions.fadeIn(0.3f, Interpolation.fade)));
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
