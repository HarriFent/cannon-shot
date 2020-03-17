package com.hfentonfearn.entitysystems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.hfentonfearn.components.CollisionComponent;
import com.hfentonfearn.components.ZoneTypeComponent;
import com.hfentonfearn.ecs.EntityFactory;
import com.hfentonfearn.ui.actions.DockActionButton;
import com.hfentonfearn.utils.Components;

import static com.hfentonfearn.ecs.EntityCategory.*;

public class CollisionSystem extends IteratingSystem {

    public CollisionSystem() {
        super(Family.all(CollisionComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entityA, float deltaTime) {
        for (Entity entityB : Components.COLLISION.get(entityA).collisionEntities){
            switch (entityA.flags) {
                case PLAYER:
                    switch (entityB.flags) {
                        case DYINGSHIP:
                            int booty = Components.CURRENCY.get(entityB).currency;
                            if (booty > 0) {
                                EntityFactory.createParticle(Components.PHYSICS.get(entityA).getPosition(), ParticleSystem.ParticleType.MONEY,0);
                                Components.CURRENCY.get(entityB).currency--;
                                Components.CURRENCY.get(entityA).currency++;
                            }
                            break;
                        case EFFECT:
                            if (Components.ZONETYPE.get(entityA).type == ZoneTypeComponent.DOCK) {
                                PlayerActionSystem.showButton(DockActionButton.class);
                            }
                            break;
                    }

                case ENEMY:
                    switch (entityB.flags) {
                        case LAND:
                        case CANNONBALL:
                        case ENEMY:
                        case PLAYER:
                            float damage = Components.PHYSICS.get(entityA).getBody().getLinearVelocity().len() + Components.PHYSICS.get(entityB).getBody().getLinearVelocity().len();
                            Components.HEALTH.get(entityA).damage(damage);
                    }
            }
        }
    }
}
