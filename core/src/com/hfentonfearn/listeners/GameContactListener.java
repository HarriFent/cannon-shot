package com.hfentonfearn.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;
import com.hfentonfearn.components.ZoneTypeComponent;
import com.hfentonfearn.ecs.EntityCategory;
import com.hfentonfearn.entitysystems.PlayerActionSystem;
import com.hfentonfearn.ui.actions.DockActionButton;
import com.hfentonfearn.utils.Components;

public class GameContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
        Components.COLLISION.get(entityA).collisionFixtures.add(contact.getFixtureB());
        Components.COLLISION.get(entityB).collisionFixtures.add(contact.getFixtureA());
        Components.COLLISION.get(entityA).collisionEntities.add(entityB);
        Components.COLLISION.get(entityB).collisionEntities.add(entityA);

        if (!beginZoneCollision(entityA, entityB)) beginZoneCollision(entityB, entityA);
    }

    private boolean beginZoneCollision(Entity entityA, Entity entityB) {
        if (entityA.flags == EntityCategory.PLAYER && entityB.flags == EntityCategory.ZONE) {
            if (Components.ZONETYPE.get(entityB).type == ZoneTypeComponent.DOCK) {
                PlayerActionSystem.showButton(DockActionButton.class);
            }
            return true;
        }
        return false;
    }

    @Override
    public void endContact(Contact contact) {

        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();
        Entity entityA = (Entity) bodyA.getUserData();
        Entity entityB = (Entity) bodyB.getUserData();
        Components.COLLISION.get(entityA).collisionFixtures.remove(contact.getFixtureB());
        Components.COLLISION.get(entityB).collisionFixtures.remove(contact.getFixtureA());

        if (bodyA.getFixtureList().size > 1) {
            if (checkMultiFixtures(bodyA, bodyB)) return;
        } else if(bodyB.getFixtureList().size > 1) {
            if (checkMultiFixtures(bodyB, bodyA)) return;
        }

        Components.COLLISION.get(entityA).collisionEntities.remove(entityB);
        Components.COLLISION.get(entityB).collisionEntities.remove(entityA);

        if (!endZoneCollision(entityA, entityB)) endZoneCollision(entityB, entityA);
    }

    private boolean checkMultiFixtures(Body bodyA, Body bodyB) {
        for (Fixture f1 : bodyA.getFixtureList()) {
            for (Fixture f2 : Components.COLLISION.get((Entity) bodyB.getUserData()).collisionFixtures) {
                if (f1.equals(f2)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean endZoneCollision(Entity entityA, Entity entityB) {
        if (entityA.flags == EntityCategory.PLAYER && entityB.flags == EntityCategory.ZONE) {
            if (Components.ZONETYPE.get(entityB).type == ZoneTypeComponent.DOCK) {
                PlayerActionSystem.hideButton(DockActionButton.class);
            }
            return true;
        }
        return false;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
