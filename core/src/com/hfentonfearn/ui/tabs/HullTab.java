package com.hfentonfearn.ui.tabs;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.hfentonfearn.ui.DockDialog;

public class HullTab extends DockTab {
    public HullTab(DockDialog dialog) {
        super("Hull", dialog.getTabPane());

        tabContent.add(new Button(skin));
        tabContent.row();
        tabContent.add(new Button(skin));
        tabContent.row();
        tabContent.add(new Button(skin));
        tabContent.row();
    }

    @Override
    protected void initialize() {

    }
}
