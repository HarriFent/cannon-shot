package com.hfentonfearn.ui.tabs;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.hfentonfearn.ui.DockDialog;
import com.hfentonfearn.ui.UpgradeItem;

public class CannonTab extends DockTab {
    public CannonTab(DockDialog dialog) {
        super("Cannon", dialog);

        tabContent.add(new Button(skin));
        tabContent.row();
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
