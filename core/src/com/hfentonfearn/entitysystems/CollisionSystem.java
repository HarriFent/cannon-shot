package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.hfentonfearn.components.CollisionComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.TypeComponent;
import com.hfentonfearn.helpers.MappersHandler;

import static com.hfentonfearn.components.TypeComponent.*;

public class CollisionSystem extends IteratingSystem {

    public CollisionSystem() {
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent collision = MappersHandler.collision.get(entity);
        Entity colliededEntity = collision.collisionEntity;
        if (colliededEntity != null) {
            TypeComponent type = colliededEntity.getComponent(TypeComponent.class);
            if (type != null) {
                switch (type.type) {
                    case LAND:
                        //Do Land Collision
                        break;
                    case ENEMY:
                        //Do Enemy Collision
                        break;
                    case CANNONBALL:
                        //Do Cannonball Collision
                        break;
                    case SCENERY:
                        //Do Scenery Collision
                        break;
                    default:
                        break;
                }
            }
            collision.collisionEntity = null;
        }
    }
}
