package com.hfentonfearn.ui;

public class UpgradeItem {

    public UpgradeItem parent;
    public UpgradeType type;
    public String name;
    public String description;
    public int cost;
    public float value;
    public boolean purchased = false;

    public enum UpgradeType {
        SPEED,
        STEERING,
        HULL,
        CARGO,
        FIRERANGE,
        FIRERATE
    }

    public UpgradeItem(UpgradeItem parent, String name, String description, int cost, float value, UpgradeType type) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.value = value;
        this.parent = parent;
        this.type = type;
    }

    public boolean canPurchase() { return parent == null ? false : parent.purchased; }
}