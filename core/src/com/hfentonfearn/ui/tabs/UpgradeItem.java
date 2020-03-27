package com.hfentonfearn.ui.tabs;

public class UpgradeItem {

    public UpgradeItem parent;
    public String name;
    public String description;
    public int cost;
    public int value;
    public boolean purchased = false;

    public UpgradeItem(UpgradeItem parent, String name, String description, int cost, int value) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.value = value;
        this.parent = parent;
    }

    public boolean canPurchase() { return parent == null ? false : parent.purchased; }
}