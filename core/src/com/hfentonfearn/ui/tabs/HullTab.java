package com.hfentonfearn.ui.tabs;

import com.hfentonfearn.ui.DockDialog;
import com.hfentonfearn.ui.UpgradeItem;

public class HullTab extends DockTab {
    public HullTab(DockDialog dialog){
        super("Hull", dialog);

        addItem(null,"Oak Hull", "A basic hull made from old oak. Only one or two big leaks.", 0, 80f);
        addItem(items.get(0),"Copper Armour", "Dull copper plating is strapped to the side of your ship.", 300, 100f);
        addItem(items.get(1),"Iron Armour", "Cast iron shielding is bolted to the upper hull.", 500, 120f);
        addItem(items.get(2),"Steel Armour", "Steel plates are welded to the hull. This should add some protection.", 700, 145f);
        addItem(items.get(3), "Iron Reinforced Hull", "Iron beams are fitted into the hulls structure. Your ship is one sturdy vessel", 900, 170f);
        addItem(items.get(4), "Steel Reinforced Hull", "Steel is combined with the wooden beams to provide a light weight, cannon resistant hull.", 1100, 200f);

        initialize();
    }

    private void addItem(UpgradeItem parent, String name, String desc, int cost, float value) {
        items.add(new UpgradeItem(parent,name, desc, cost, value, UpgradeItem.UpgradeType.HULL));
    }
}
