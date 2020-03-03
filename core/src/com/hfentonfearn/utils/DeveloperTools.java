package com.hfentonfearn.utils;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.ShipMovementComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.entitysystems.CameraSystem;

public class DeveloperTools {

    public static void spawnEnemyAtCursor() {
        PooledEngine engine = GameManager.getEngine();
        Vector2 pos = engine.getSystem(CameraSystem.class).screenToWorldCords(Gdx.input.getX(), Gdx.input.getY());
        EntityFactory.createEnemyShip(pos, 40);
    }

    public static void incPlayerSpeedState() {
        PooledEngine engine = GameManager.getEngine();
        Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
        if (player != null) {
            ShipMovementComponent comp = Components.SHIP_MOVEMENT.get(player);
            comp.impulseVel++;
        }
    }

    public static void decPlayerSpeedState() {
        PooledEngine engine = GameManager.getEngine();
        Entity player = engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0);
        if (player != null) {
            ShipMovementComponent comp = Components.SHIP_MOVEMENT.get(player);
            comp.impulseVel--;
        }
    }
}
