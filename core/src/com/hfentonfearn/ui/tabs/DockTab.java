package com.hfentonfearn.ui.tabs;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.hfentonfearn.utils.AssetLoader;

public abstract class DockTab {

    protected TextButton btnTab;
    protected Table tabContent;
    protected final Skin skin = AssetLoader.skin;

    public DockTab(String tabName, Container<Table> tabPane) {
        btnTab = new TextButton(tabName, skin,"tab");
        btnTab.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tabPane.setActor(tabContent);
            }
        });
        tabContent = new Table();
    }

    public Table getTabContent() {
        return tabContent;
    }

    public TextButton getBtnTab() {
        return btnTab;
    }
}
