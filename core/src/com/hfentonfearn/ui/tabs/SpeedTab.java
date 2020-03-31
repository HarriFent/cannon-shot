package com.hfentonfearn.ui.tabs;

import com.hfentonfearn.ui.DockDialog;
import com.hfentonfearn.ui.UpgradeItem;

public class SpeedTab extends DockTab {

    public SpeedTab(DockDialog dialog) {
        super("Speed", dialog);

        addItem(null,"Torn Rags", "These torn rags barely help you get you ship from A to B.", 0, 20);
        addItem(items.get(0),"Flax Sails", "These basic sails catch the wind enough to get the ship moving.", 300, 30);
        addItem(items.get(1),"Hemp Sails", "Although pretty heavy, these hemp sails are more resilient.", 500, 35);
        addItem(items.get(2),"Cotton Sails", "Light weight cotton is stitched to to provide a good speed.", 600, 40);
        addItem(items.get(3), "Silk Sails", "Luxury sails fit only for kings increase the speed drastically.", 800, 50);
        addItem(items.get(4), "Cursed Sails", "Sails cursed with the souls of dead sailors. Their spirits carry the wind.", 1000, 60);

        initialize();
    }

    private void addItem(UpgradeItem parent, String name, String desc, int cost, float value) {
        items.add(new UpgradeItem(parent,name, desc, cost, value, UpgradeItem.UpgradeType.SPEED));
    }
}
