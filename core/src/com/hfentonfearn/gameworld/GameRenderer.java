package com.hfentonfearn.gameworld;

import com.badlogic.ashley.core.Entity;

public class GameRenderer {

    private final GameWorld gameWorld;

    public GameRenderer(GameWorld gameWorld) {

        this.gameWorld = gameWorld;
    }

    public void render(float delta, float runtime) {
        for (Entity e : gameWorld.getEntities()) {

        }
    }

    public void dispose() {

    }
}
