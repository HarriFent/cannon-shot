package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.hfentonfearn.objects.BootyItem;

public class InventoryComponent implements Component, Poolable {

    public Array<BootyItem> items;

    private InventoryComponent() {}

    public InventoryComponent init () {
        items = new Array<>();
        return this;
    }

    public void setSize(int inventorySize) {
        items.setSize(inventorySize);
    }

    @Override
    public void reset() {
        items = null;
    }
}
