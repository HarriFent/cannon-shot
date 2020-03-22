package com.hfentonfearn.ui.tabs;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class SpeedTab extends DockTab {

    public SpeedTab(Container<Table> tabPane) {
        super("Speed", tabPane);

        tabContent.add(new Button(skin));
        tabContent.row();
    }
}
