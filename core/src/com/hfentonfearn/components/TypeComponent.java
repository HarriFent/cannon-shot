package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {
    public static final String LAND = "LAND";
    public static final String PLAYER = "PLAYER";
    public static final String ENEMY = "ENEMY";
    public static final String CANNONBALL = "CANNONBALL";
    public static final String SCENERY = "SCENERY";

    public String type;

    public TypeComponent(String type) {
        this.type = type;
    }
}
