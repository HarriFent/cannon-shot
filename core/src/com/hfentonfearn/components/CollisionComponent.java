package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Pool.Poolable;

import java.util.HashSet;

public class CollisionComponent implements Component, Poolable {
    public HashSet<Entity> collisionEntities;

    private CollisionComponent() {}

    public CollisionComponent init() {
        collisionEntities = new HashSet<>();
        return this;
    }

    @Override
    public void reset() {
        collisionEntities = null;
    }
}
