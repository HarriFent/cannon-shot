package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.hfentonfearn.GameManager;
import com.hfentonfearn.components.ShipStatisticComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.utils.AssetLoader;

public class EnemySpawningSystem extends EntitySystem {

    ImmutableArray<Entity> enemies;

    @Override
    public void addedToEngine(Engine engine) {
        enemies = engine.getEntitiesFor(Family.all(ShipStatisticComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        int numOfEnemies = enemies.size();
        if (numOfEnemies < 30) {
            EntityFactory.createEnemyShip(getValidSpawnPoint(), MathUtils.random(30,50));
        }
    }

    @Override
    public boolean checkProcessing () {
        return !GameManager.isPaused();
    }

    private Vector2 getValidSpawnPoint() {
        while (true) {
            Vector2 spawnPoint = new Vector2(MathUtils.random(AssetLoader.map.width), MathUtils.random(AssetLoader.map.height));
            for (MapObject obj : AssetLoader.map.spawnzones) {
                RectangleMapObject object = (RectangleMapObject) obj;
                if (object.getRectangle().contains(spawnPoint))
                    return spawnPoint;
            }
        }
    }
}
