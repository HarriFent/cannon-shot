package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class ZoneTypeComponent implements Component {
    public static final int ENEMYSPAWN = 0;
    public static final int DOCK = 1;

    public int type;

    public ZoneTypeComponent(int type) {
        this.type = type;
    }
}
