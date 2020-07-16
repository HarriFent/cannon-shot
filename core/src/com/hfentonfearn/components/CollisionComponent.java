package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Pool.Poolable;

import java.util.HashSet;

public class CollisionComponent implements Component, Poolable {
    public HashSet<Entity> collisionEntities;
    public HashSet<Fixture> collisionFixtures;

    private CollisionComponent() {}

    public CollisionComponent init() {
        collisionEntities = new HashSet<>();
        collisionFixtures = new HashSet<>();
        return this;
    }

    @Override
    public void reset() {
        collisionEntities = null;
        collisionFixtures = null;
    }
}
