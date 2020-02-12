package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;

public class TypeComponent implements Component {
    public static final int LAND = 0;
    public static final int PLAYER = 1;
    public static final int ENEMY = 2;
    public static final int CANNONBALL = 3;

    public int type;
}
