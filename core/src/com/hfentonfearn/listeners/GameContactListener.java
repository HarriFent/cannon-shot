package com.hfentonfearn.listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
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
        Entity entityA = (Entity) contact.getFixtureA().getBody().getUserData();
        Entity entityB = (Entity) contact.getFixtureB().getBody().getUserData();
        Components.COLLISION.get(entityA).collisionEntities.remove(entityB);
        Components.COLLISION.get(entityB).collisionEntities.remove(entityA);

        if (!endZoneCollision(entityA, entityB)) endZoneCollision(entityB, entityA);
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
