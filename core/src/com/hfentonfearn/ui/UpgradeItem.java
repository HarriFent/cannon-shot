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
        FIRERATE;

        @Override
        public String toString() {
            switch (this) {
                case FIRERANGE:
                    return "Cannon range";
                case STEERING:
                    return "Steering strength";
                case FIRERATE:
                    return "Cannon fire rate";
                case SPEED:
                    return "Speed level";
                case CARGO:
                    return "Cargo capacity";
                case HULL:
                    return "Hull points";
                default:
                    return "Enum Not Found";
            }
        }
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