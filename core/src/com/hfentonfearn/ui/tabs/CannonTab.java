package com.hfentonfearn.ui.tabs;

import com.hfentonfearn.ui.DockDialog;
import com.hfentonfearn.ui.UpgradeItem;

public class CannonTab extends DockTab {

    /*

    Fire Rate
    Untrained crew members = 300
    Powder bags = 200
    Cotton wads = 150
    New sponger stick = 100
    Longer Slow Match = 75

    Fire Range
    Old rusty Cannons = 6f
    De-rust cannons = 7.5f
    Sturdy cannons = 9f
    Larger powder bags = 11f
    Steel balls = 14f
     */

    public CannonTab(DockDialog dialog) {
        super("Cannon", dialog);

        addItem(null, "Untrained Crew Members", "Your clueless crew have no idea how to effectively fire a cannon.", 0, 300, true);
        addItem(items.get(0), "Powder Bags", "You pack the gun powder into bags before the fights. This will save reload time.", 290, 200, true);
        addItem(items.get(1), "Cotton Wads", "Cotton wads burn faster and leave less residue in the cannon.", 578, 150, true);
        addItem(items.get(2), "Sponger Stick", "Investing in sponger sticks, the cannons are ready to fire sooner.", 813, 100, true);
        addItem(items.get(3), "Longer Slow Match", "Lighting the cannons has never been so easy. Fire in the hole!", 1030, 75, true);

        addItem(null, "Old Rusty Cannons", "You found these rusting metal tubes in an abandoned ship. Hopefully they still work.", 0, 6f, false);
        addItem(items.get(5), "De-rust Cannons", "With a bit of oil and elbow grease, the cannons are in some what of a working order.", 251, 7.5f, false);
        addItem(items.get(6), "Sturdy Cannons", "Out with the old, in with the new. These shining cannons can fire much further.", 507, 9f, false);
        addItem(items.get(7), "Larger Powder Bags", "The bigger, the better. Why didn't the crew think of this before?", 698, 11f, false);
        addItem(items.get(8), "Steel Cannonballs", "Cannonballs crafted with more precision fit snug into the barrel creating more pressure.", 950, 14f, false);

        initialize();
    }

    private void addItem(UpgradeItem parent, String name, String desc, int cost, float value, boolean rate) {
        if (rate) {
            items.add(new UpgradeItem(parent, name, desc, cost, value, UpgradeItem.UpgradeType.FIRERATE));
        } else {
            items.add(new UpgradeItem(parent, name, desc, cost, value, UpgradeItem.UpgradeType.FIRERANGE));
        }
    }

    @Override
    protected void initialize() {
        for (int index = 0; index < items.size / 2; index++) {
            tabContent.add(createButton(index, items.get(index))).size(70);
            tabContent.add(getTabLabel(items.get(index))).width(200).padLeft(10).padRight(20);
            tabContent.add(createButton(index, items.get(index + 5))).size(70).padLeft(20);
            tabContent.add(getTabLabel(items.get(index+ 5))).width(200).padLeft(10);
            tabContent.row();
        }
    }
}
