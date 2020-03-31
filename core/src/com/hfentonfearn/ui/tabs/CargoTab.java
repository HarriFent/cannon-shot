package com.hfentonfearn.ui.tabs;

import com.hfentonfearn.ui.DockDialog;
import com.hfentonfearn.ui.UpgradeItem;
/*
Small Cargo Hold = 3f
Large Cargo Hold = 5f
Master Cargo Hold = 7f
Drunk Crew = 9f
Hungry Pirates = 12f
 */
public class CargoTab extends DockTab {
    public CargoTab(DockDialog dialog){
        super("Cargo", dialog);

        addItem(null, "Small Cargo Hold", "A small cupboard at the back of the ship can store a few items.", 0, 3);
        addItem(items.get(0), "Large Cargo Hold", "Taking the cupboard wall out makes room for a few more things.", 0, 5);
        addItem(items.get(1), "Master Cargo Hold", "Fixing up the ship reveals additional spaces to store your cargo.", 0, 7);
        addItem(items.get(2), "Drunk Crew", "The crew have a huge party and drink all the rum supplies freeing space.", 0, 9);
        addItem(items.get(3), "Hungry Crew", "The crew value cargo over food. They throw the supplies in the ocean.", 0, 12);

        initialize();
    }

    @Override
    protected void addItem(UpgradeItem parent, String name, String desc, int cost, float value) {
        items.add(new UpgradeItem(parent,name, desc, cost, value, UpgradeItem.UpgradeType.CARGO));
    }
}
