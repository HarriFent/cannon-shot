package com.hfentonfearn.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

import java.util.HashSet;

public class CollisionComponent implements Component {
    public HashSet<Entity> collisionEntities = new HashSet<>();
}
