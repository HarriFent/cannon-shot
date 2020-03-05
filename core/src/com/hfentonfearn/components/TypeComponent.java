package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TypeComponent implements Component, Poolable {
    public static final String LAND = "LAND";
    public static final String SCENERY = "SCENERY";
    public static final String CLOUD = "CLOUD";
    public static final String PLAYER = "PLAYER";
    public static final String ENEMY = "ENEMY";
    public static final String CANNONBALL = "CANNONBALL";

    public String type;

    private TypeComponent() {}

    public TypeComponent init (String type) {
        this.type = type;
        return this;
    }

    @Override
    public void reset() {
        type = null;
    }
}
