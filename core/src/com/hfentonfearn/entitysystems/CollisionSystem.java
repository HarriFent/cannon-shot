package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.hfentonfearn.components.CollisionComponent;
import com.hfentonfearn.components.TextureComponent;
import com.hfentonfearn.components.TransformComponent;
import com.hfentonfearn.helpers.AssetLoader;
import com.hfentonfearn.helpers.MappersHandler;

public class CollisionSystem extends EntitySystem {

    private ImmutableArray<Entity> entities;
    private MapObjects objects;

    public CollisionSystem() {
        objects = AssetLoader.map.getLayers().get("collision").getObjects();
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(CollisionComponent.class).get());
    }

    public void update(float deltaTime) {
        for (Entity e : entities) {
            CollisionComponent cComp =  MappersHandler.collision.get(e);
            TransformComponent tComp = MappersHandler.transform.get(e);

            cComp.collisionShape.setPosition(tComp.getX(), tComp.getY());
            cComp.collisionShape.setRotation(tComp.getAngle());
            cComp.collisionShape.setScale(tComp.getScaleX(),tComp.getScaleY());
        }
    }
}
