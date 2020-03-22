package com.hfentonfearn.ui.tabs;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class HullTab extends DockTab {
    public HullTab(Container<Table> tabPane) {
        super("Hull", tabPane);

        tabContent.add(new Button(skin));
        tabContent.row();
        tabContent.add(new Button(skin));
        tabContent.row();
        tabContent.add(new Button(skin));
        tabContent.row();
    }
}
