package com.hfentonfearn.utils;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.entitysystems.CameraSystem;

public class DeveloperTools {

    public static void spawnEnemyAtCursor() {
        PooledEngine engine = GameManager.getEngine();
        Vector2 pos = engine.getSystem(CameraSystem.class).screenToWorldCords(Gdx.input.getX(), Gdx.input.getY());
        EntityFactory.createEnemyShip(pos, 40);
    }
}
