package com.hfentonfearn.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.entitysystems.CameraSystem;
import com.hfentonfearn.entitysystems.ParticleSystem;

public class DeveloperTools {

    public static void spawnEnemyAtCursor() {
        PooledEngine engine = GameManager.getEngine();
        Vector2 pos = engine.getSystem(CameraSystem.class).screenToWorldCords(Gdx.input.getX(), Gdx.input.getY());
        EntityFactory.createEnemyShip(pos, 40);
    }

    public static void incPlayerSpeedState() {
        PooledEngine engine = GameManager.getEngine();
        Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
        if (player != null)
            Components.STATS.get(player).incSpeed();
    }

    public static void decPlayerSpeedState() {
        PooledEngine engine = GameManager.getEngine();
        Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
        if (player != null)
            Components.STATS.get(player).decSpeed();
    }

    public static void spawnWaterParticle() {
        PooledEngine engine = GameManager.getEngine();
        Vector2 pos = engine.getSystem(CameraSystem.class).screenToWorldCords(Gdx.input.getX(), Gdx.input.getY());
        EntityFactory.createParticle(pos, ParticleSystem.ParticleType.WATER);
    }
}
