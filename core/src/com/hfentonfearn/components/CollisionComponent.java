package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Polygon;

public class CollisionComponent implements Component {
    public Polygon collisionShape;
    public boolean isColliding;

    public CollisionComponent(Polygon poly) {
        collisionShape = poly;
    }
}
