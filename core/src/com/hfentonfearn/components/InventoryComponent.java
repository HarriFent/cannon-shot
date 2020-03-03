package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Array;
import com.hfentonfearn.objects.BootyItem;

public class InventoryComponent implements Component {

    public float currency;
    public Array<BootyItem> items;

    public void setSize(int inventorySize) {
        items.setSize(inventorySize);
    }
}
