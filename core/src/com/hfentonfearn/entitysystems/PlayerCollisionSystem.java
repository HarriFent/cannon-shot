package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.hfentonfearn.components.CollisionComponent;
import com.hfentonfearn.components.PlayerComponent;
import com.hfentonfearn.components.TypeComponent;
import com.hfentonfearn.ecs.Components;

import static com.hfentonfearn.components.TypeComponent.*;

public class PlayerCollisionSystem extends IteratingSystem {

    public PlayerCollisionSystem() {
        super(Family.all(CollisionComponent.class, PlayerComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CollisionComponent collision = Components.collision.get(entity);
        for (Entity collidedEntity : collision.collisionEntities) {
            if (collidedEntity != null) {
                TypeComponent type = collidedEntity.getComponent(TypeComponent.class);
                if (type != null) {
                    switch (type.type) {
                        case LAND:
                            //Do Land Collision
                            System.out.println("LAND COLLISION");
                            break;
                        case ENEMY:
                            //Do Enemy Collision
                            System.out.println("ENEMY COLLISION");
                            break;
                        case CANNONBALL:
                            //Do Cannonball Collision
                            System.out.println("CANNONBALL COLLISION");
                            break;
                        case SCENERY:
                            //Do Scenery Collision
                            System.out.println("SCENERY COLLISION");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
}
