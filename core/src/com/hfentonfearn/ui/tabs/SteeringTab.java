package com.hfentonfearn.ui.tabs;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.hfentonfearn.ui.DockDialog;

public class SteeringTab extends DockTab {
    public SteeringTab(DockDialog dialog) {
        super("Steering", dialog.getTabPane());


        tabContent.add(new Button(skin));
        tabContent.row();
        tabContent.add(new Button(skin));
        tabContent.row();
    }

    @Override
    protected void initialize() {

    }
}
