package com.hfentonfearn.ui.tabs;

import com.hfentonfearn.ui.DockDialog;
import com.hfentonfearn.ui.UpgradeItem;

public class SteeringTab extends DockTab {
    public SteeringTab(DockDialog dialog){
        super("Steering", dialog);

        addItem(null,"Basic Rudder", "Not much more than a plank of wood sending the ship in a general direction.", 0, 0.035f);
        addItem(items.get(0),"Reinforced Rudder", "Reinforced with iron hinges. Your vessel is starting to look sea worthy.", 1, 0.65f);
        addItem(items.get(1),"Advanced Helm", "Your wooden wheel is in need or replacement. This iron ellipse is a perfect upgrade.", 1, 0.85f);
        addItem(items.get(2),"Double Rudder", "Why have one, when you can have two? Twice the wood, twice the control.", 1, 0.135f);
        addItem(items.get(3), "Double Reinforced Rudder", "These rudders are the best on the market. You can't make them better.", 1, 0.165f);
        addItem(items.get(4), "Blessed Helm", "Voodoo blesses your helm, turning the wheel faster than any human can.", 1, 0.2f);

        initialize();
    }

    @Override
    protected void addItem(UpgradeItem parent, String name, String desc, int cost, float value) {
        items.add(new UpgradeItem(parent,name, desc, cost, value, UpgradeItem.UpgradeType.STEERING));
    }
}
