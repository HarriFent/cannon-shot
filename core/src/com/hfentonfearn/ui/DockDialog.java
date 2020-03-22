package com.hfentonfearn.ui;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.ui.tabs.*;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class DockDialog extends Window {

    Table contentTable;
    private Skin skin;
    private DockTab[] tabs = new DockTab[5];
    private Container<Table> tabPane = new Container();

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

        tabs[0] = new SpeedTab(tabPane);
        tabs[1] = new SteeringTab(tabPane);
        tabs[2] = new HullTab(tabPane);
        tabs[3] = new CannonTab(tabPane);
        tabs[4] = new CargoTab(tabPane);

        ButtonGroup buttonGroup = new ButtonGroup();
        for (DockTab tab : tabs) {
            buttonGroup.add(tab.getBtnTab());
        }
        buttonGroup.setMaxCheckCount(1);
        buttonGroup.setMinCheckCount(0);
        buttonGroup.setUncheckLast(true);

        tabPane.setActor(tabs[0].getTabContent());
        contentTable.add(tabs[0].getBtnTab()).prefWidth(200);
        contentTable.add(tabs[1].getBtnTab()).prefWidth(200);
        contentTable.add(tabs[2].getBtnTab()).prefWidth(200);
        contentTable.add(tabs[3].getBtnTab()).prefWidth(200);
        contentTable.add(tabs[4].getBtnTab()).prefWidth(200);
        contentTable.row();
        contentTable.add(tabPane).colspan(5).prefHeight(500);
        contentTable.row();

        TextButton closeButton = new TextButton("Close", skin, "inventory");
        closeButton.pad(10);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameManager.resume();
                remove();
            }
        });

        contentTable.add(new TextButton("Repair Hull",skin)).prefWidth(200).left().colspan(4);
        contentTable.add(closeButton).right();
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
