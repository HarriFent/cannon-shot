package com.hfentonfearn.ui.tabs;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.hfentonfearn.ui.DockDialog;
import com.hfentonfearn.ui.UpgradeItem;

public class HullTab extends DockTab {
    public HullTab(DockDialog dialog) {
        super("Hull", dialog);

        tabContent.add(new Button(skin));
        tabContent.row();
        tabContent.add(new Button(skin));
        tabContent.row();
        tabContent.add(new Button(skin));
        tabContent.row();
    }

    @Override
    protected void addItem(UpgradeItem parent, String name, String desc, int cost, float value) {

    }

    @Override
    protected void initialize() {

    }
}
