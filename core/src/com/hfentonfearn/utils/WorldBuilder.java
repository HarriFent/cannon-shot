package com.hfentonfearn.utils;

import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.ecs.EntityFactory;

public class WorldBuilder {

    private float width;
    private float height;

    public WorldBuilder(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void createWorld() {
        EntityFactory.createPlayer(new Vector2(500,500));
    }
}
